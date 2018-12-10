#include <cpu.h>
#include <inttypes.h>
#include <stddef.h>
#include <string.h>
#include <stdbool.h>
#include "start.h"


void affichage_haut_droite(size_t size, char* chaine);

void tic_PIT(void);

void init_traitant_IT(int32_t num_IT, void (*traitant)(void));

void reglage_frequence(void);

void masque_IRQ(uint32_t num_IRQ, bool masque);
