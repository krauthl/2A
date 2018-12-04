#include <inttypes.h>
#include <stdlib.h>
#include <string.h>
#include <stddef.h>
#include <cpu.h>

uint16_t *ptr_mem(uint32_t lig, uint32_t col);

void ecrit_car(uint32_t lig, uint32_t col, char c);

void efface_ecran(void);

void place_curseur(uint32_t lig, uint32_t col);

void traite_car(char c);


void defilement(void);

void console_putbytes(char *chaine, int32_t taille);
