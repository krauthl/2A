#include <cpu.h>
#include <inttypes.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>
#include <stdio.h>
#include "ecran.h"
#include "temps.h"

/*Cherche la fonction traitant_IT_32 qui se trouve dans traitants.s*/
void traitant_IT_32(void);

void kernel_start(void)
{
    efface_ecran();

    //Démasquage de l'IRQ0
    masque_IRQ(0, false);
    reglage_frequence();
    init_traitant_IT(32, traitant_IT_32);

    //Démasquage des interruptions externes
    sti();

    while (1) {
        hlt();
    }
}
