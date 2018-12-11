#include <cpu.h>
#include <inttypes.h>
#include <stddef.h>
#include <string.h>
#include <stdio.h>
#include "ecran.h"
#include "temps.h"
#include "processus.h"

extern void ctx_sw(int*, int*);

StructureProcessus struct_idle;
StructureProcessus struct_proc1;
StructureProcessus struct_proc2;
StructureProcessus struct_proc3;

int32_t init_process(StructureProcessus *str_proc, int pid, char* nom){
    str_proc->pid = pid;
    str_proc->nom = nom;
    str_proc->etat = Activable;
    str_proc->zone_sauvegarde[1] = (int)(&str_proc->pile[TAILLE_PILE-1]);
    str_proc->pile[TAILLE_PILE -1] = (int)code;
    return pid;
}

void init_struct_processus(void){
    struct_idle.pid = 0;
    struct_idle.nom = "idle";
    struct_idle.etat = Elu;
    struct_idle.next = &struct_proc1;

    init_process(&struct_proc1, 1, "proc1");
    struct_proc1.next = &struct_proc2;

    init_process(&struct_proc2, 2, "proc2");
    struct_proc2.next = &struct_proc3;

    init_process(&struct_proc3, 3, "proc3");
    struct_proc3.next = NULL;
}

void ordonnance(void){

    StructureProcessus *str_proc;
    for (str_proc = &struct_idle; str_proc->next; str_proc = str_proc->next){
        if (str_proc->etat == Elu){
            str_proc->etat = Activable;
            (str_proc->next)->etat = Elu;
            ctx_sw(str_proc->zone_sauvegarde, (*(str_proc->next)).zone_sauvegarde);
        }
    }

    /*Cas où on est arrivé au dernier et le dernier est le processus élu*/
    if (str_proc->etat == Elu){
        str_proc->etat = Activable;
        struct_idle.etat = Elu;
        ctx_sw(str_proc->zone_sauvegarde, struct_idle.zone_sauvegarde);
    }
}

char* mon_nom(void){
    StructureProcessus *str_proc;
    char *nom;
    for (str_proc = &struct_idle; str_proc->next; str_proc = str_proc->next){
        if (str_proc->etat == Elu){
            nom = str_proc->nom;
        }
    }
    /*Cas où on est arrivé au dernier et le dernier est le processus élu*/
    if (str_proc->etat == Elu){
        nom = str_proc->nom;
    }
    return nom;
}

int mon_pid(void){
    StructureProcessus *str_proc;
    int pid;
    for (str_proc = &struct_idle; str_proc->next; str_proc = str_proc->next){
        if (str_proc->etat == Elu){
            pid = str_proc->pid;
        }
    }
    /*Cas où on est arrivé au dernier et le dernier est le processus élu*/
    if (str_proc->etat == Elu){
        pid = str_proc->pid;
    }
    return pid;
}


void idle(void)
{
    for (;;){
        printf("[%s] pid = %i\n", mon_nom(), mon_pid());
        sti();
        ordonnance();
        cli();
    }
}


void code(void){
    for (;;) {
        printf("[%s] pid = %i\n", mon_nom(), mon_pid());
        sti();
        ordonnance();
        cli();
    }
}
