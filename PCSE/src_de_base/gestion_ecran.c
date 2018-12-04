#include <inttypes.h>
#include <stdlib.h>
#include "gestion_ecran.h"
#include <string.h>
#include <stddef.h>
#include <cpu.h>

uint16_t CURSOR_POS = 0;

// Returns pointer to memory case with given coordinates
uint16_t *ptr_mem(uint32_t lig, uint32_t col){
    uint16_t *ptr = (uint16_t*) (0xB8000 + 2*(col + lig * 80)); 
    return ptr;
}

// Writes c into given coordinates
void ecrit_car(uint32_t lig, uint32_t col, char c){
    uint16_t *target_ptr = ptr_mem(lig, col);
    memcpy(target_ptr, &c, sizeof(char));
}


// Clears screen
// Nouvelle idee : ecrire ' '.FOND_NOIR,ETC à 0xB8000 * 80*25
void efface_ecran(void){
    uint32_t lig = 0;
    uint32_t col = 0;

    while(col < 80){
        lig = 0;
        while(lig < 25){
            ecrit_car(lig, col, ' ');
            lig++;
        }
        col++;
    }
    CURSOR_POS = 0;
    place_curseur(0, 0);
}

// Sets cursor's position to given coordinates
void place_curseur(uint32_t lig, uint32_t col){
    uint16_t pos = col + lig * 80;
    outb(0x0F, 0x3D4);
    outb(pos & 0xFF, 0x3D5);
    outb(0x0E, 0x3D4);
    outb(pos >> 8, 0x3D5);
}

// Process given char
// NOTE: Inchallah ca marche
void traite_car(char c){
    if(32 <= c && c <= 126){
        ecrit_car(CURSOR_POS/80, CURSOR_POS%80, c);
        CURSOR_POS++;
    } else {
        switch(c){
            case '\b':
                if(CURSOR_POS > 0){
                    CURSOR_POS--;
                }
            break;
            case '\t':
                if(CURSOR_POS%80 != 79){
                    CURSOR_POS += (8 - CURSOR_POS%8);
                }
            break;
            case '\n':
                CURSOR_POS += 80 - CURSOR_POS%80;
            break;
            case '\f':
                efface_ecran();
            break;
            case '\r':
                CURSOR_POS -= CURSOR_POS%80;
        }
    }
    place_curseur(CURSOR_POS/80, CURSOR_POS%80);
}


// Scroll up screen's content
// NOTE: handle cursor's position?
void defilement(void){
     uint32_t lig = 0;
     uint32_t col = 0;

     while(col < 80){
         lig = 0;
         uint16_t *old_char = ptr_mem(lig, col);
         while(lig < 25){
             uint16_t *new_char = ptr_mem(lig+1, col);
             memmove(old_char, new_char, sizeof(uint16_t));
             old_char = new_char;
             lig++;
         }
         col++;
     }   
}

// Prints a string of max size taille on screen
// NOTE: taille might be negative => broken
void console_putbytes(char *chaine, int32_t taille){
    uint32_t i = 0;
    while(i < taille){
        traite_car(*(chaine+i));
        i++;
    }
}
