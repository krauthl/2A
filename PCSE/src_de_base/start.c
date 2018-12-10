#include <cpu.h>
#include <inttypes.h>
#include <stddef.h>
#include <string.h>
#include <stdio.h>
//#include "ecran.h"
#include "gestion_ecran.h" //A MODIFIER ET REMPLACER PAR ecran.h quand ça sera fini
#include "temps.h"
#include "processus.h"


//Cherche la fonction traitant_IT_32 qui se trouve dans traitants.S
void traitant_IT_32(void);

void kernel_start(void)
{
    //Fonction qui sert de point d'entrée au noyau

    efface_ecran();
    place_curseur(0,0);

    //Initialisation des structures de processus
    // init_struct_processus();
    // //Démarrage du processus par défaut
    // idle();


    init_traitant_IT(32, traitant_IT_32);
    reglage_frequence();
    masque_IRQ(0, false);

    //Démasquage des interruptions externes
    sti();

    while (1) {
        // cette fonction arrete le processeur
        hlt();
    }
}
