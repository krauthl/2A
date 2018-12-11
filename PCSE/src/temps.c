#include <cpu.h>
#include <stddef.h>
#include <string.h>
#include <stdio.h>
#include <stdbool.h>
#include <inttypes.h>
#include "ecran.h"
#include "temps.h"
#include "segment.h"
#define QUARTZ 0x1234DD
#define CLOCK_FREQ 50

unsigned long nb_tic = 0; //Nombre de "tics"
unsigned long nb_s = 0; //Nombre de secondes : 1 seconde pour CLOCK_FREQ tics

bool horloge;

void affichage_haut_droite(char* chaine){
    /*Affichage d'une chaine en haut à droite de l'écran*/
    for (int i=0; i<strlen(chaine); i++){
        ecrit_car(0, 65+i, chaine[i]);
    }
}

void tic_PIT(void){
    /*Ecriture du traitant de l'interruption 32*/

    //Signaler au contrôleur d'interruptions qu'on commence à traiter une interruption
    outb(0x20, 0x20);

    if (nb_tic == 0){
        affichage_haut_droite("0:0:0");
    }
    nb_tic++;
    if (nb_tic%50==0){
        nb_s++;

        int heures = nb_s/3600;
        int minutes = (nb_s - heures*3600)/60;
        int secondes = nb_s - heures*3600 - minutes*60;

        char* chaine_a_afficher = "          ";
        sprintf(chaine_a_afficher, "%u:%u:%u", heures, minutes, secondes);
        affichage_haut_droite(chaine_a_afficher);
    }
}


void reglage_frequence(void){
    /*Réglage de la fréquence de l'horloge*/
    outb(0x34, 0x43);
    outb((QUARTZ/CLOCK_FREQ)%256, 0x40); // envoi des 8 bits de poids faible
    outb((QUARTZ / CLOCK_FREQ) >> 8, 0x40); // envoi des 8 bits de poids fort

    horloge = true; //cettiable va servir à améliorer le défilement
}


void init_traitant_IT(int32_t num_IT, void (*traitant)(void)){
    /*Initialise la table d'interruptions*/
    /*Prend en paramètre un traitant et un numéro d'interruption*/
    uint32_t *vect = (uint32_t *)(0x1000 + 2*sizeof(uint32_t)*num_IT);
    *vect = ((uint32_t)traitant & 0x0000FFFF) | (KERNEL_CS<<16);
    *(vect+1) = 0x8E00 | ((uint32_t)traitant & 0xFFFF0000);
}


void masque_IRQ(uint32_t num_IRQ, bool masque){
    /*Fonction permettant de masquer ou démasquer l'irq num_IRQ*/
    uint32_t valeur_actuelle = inb(0x21);
    uint32_t filter = (uint32_t)1 << num_IRQ;
    if (masque == true){
        //Forcer le bit N à 1
        outb(valeur_actuelle | filter, 0x21);
    } else {
        //Forcer le bit N à 0
        outb(valeur_actuelle & ~filter, 0x21);
    }
}
