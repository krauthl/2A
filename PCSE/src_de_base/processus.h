#include <cpu.h>
#include <inttypes.h>
#include <stddef.h>
#include <string.h>
#include "gestion_ecran.h" //A MODIFIER
#include "temps.h"

//#define TAILLE_MAX_NOM 10
#define TAILLE_ZONE_SAUVEGARDE 5
#define TAILLE_PILE 512


/*Etat élu : 0, état activable : 1 */
enum Etat { Elu, Activable};


typedef struct StructureProcessus StructureProcessus;
struct StructureProcessus{
    int pid;
    char* nom;
    enum Etat etat;
    int zone_sauvegarde[TAILLE_ZONE_SAUVEGARDE];
    int pile[TAILLE_PILE];
    StructureProcessus *next;
};

int32_t init_process(StructureProcessus *str_proc, int pid, char* nom);

void idle(void);

void code(void); //code des fonctions proc1, proc2 et proc3

void init_struct_processus(void);

void ordonnance(void);

char* mon_nom(void);

int mon_pid(void);
