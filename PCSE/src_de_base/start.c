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

    //Démasquage des interruptions externes

    masque_IRQ(0, false);
    reglage_frequence();
    init_traitant_IT(32, traitant_IT_32);
    sti();

    while (1) {
        // cette fonction arrete le processeur
        hlt();
    }
}
