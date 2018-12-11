#include <cpu.h>
#include <inttypes.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>
#include <stdio.h>
#include "ecran.h"
#include "temps.h"
#include "processus.h"

/*Cherche la fonction traitant_IT_32 qui se trouve dans traitants.s*/
void traitant_IT_32(void);

void kernel_start(void)
{
    efface_ecran();

    init_traitant_IT(32, traitant_IT_32);
    reglage_frequence();
    masque_IRQ(0, false);

    init_struct_processus();
    idle();

    while (1) {
        hlt();
    }
}
