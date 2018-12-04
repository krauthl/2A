#include <cpu.h>
#include <inttypes.h>
#include <stddef.h>
#include <string.h>

uint16_t *ptr_mem(uint32_t lig, uint32_t col);

void ecrit_car(uint32_t lig, uint32_t col, char c);

void efface_ecran(void);

void place_curseur(uint32_t lig, uint32_t col);
