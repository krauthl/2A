#include <cpu.h>
#include <inttypes.h>
#include <stddef.h>
#include <string.h>
#include <stdbool.h>
#include "start.h"

extern bool horloge;
extern unsigned long nb_tic; //Nombre de "tics"
extern unsigned long nb_s; //Nombre de secondes : 1 seconde pour CLOCK_FREQ tics



void affichage_haut_droite(char* chaine);

void tic_PIT(void);

void init_traitant_IT(int32_t num_IT, void (*traitant)(void));

void reglage_frequence(void);

void masque_IRQ(uint32_t num_IRQ, bool masque);

void clock_init();
