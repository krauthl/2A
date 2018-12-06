#include <cpu.h>
#include <inttypes.h>
#include <stddef.h>
#include <string.h>
#include <stdio.h>
#include <stdbool.h>
#include "ecran.h"
#include "temps.h"
#include "segment.h"
#define QUARTZ 0x1234DD
#define CLOCK_FREQ 50

int nb_tic = 0; //Nombre de "tics"
int nb_s = 0; //Nombre de secondes : 1 seconde pour 50 tics


void affichage_haut_droite(size_t size, char* chaine){
  uint32_t colonne = 65;
  for (int i=0; i<size; i++){
    ecrit_car(0, colonne+i, chaine[i]);
  }
}


void tic_PIT(void){
  //Ecriture du traitant de l'interruption 32

  //Signaler au contrôleur d'interruptions qu'on commence à traiter une interruption
  outb(0x20, 0x20);

  //On compte les tics de l'horloge et on ajoute une seconde tous les 50 tics
  nb_tic++;
  if (nb_tic%50==0){
    nb_s++;
  }

  // //Afficher l'heure
  // int heures = nb_s/3600;
  // int minutes = (nb_s - heures*3600)/60;
  // int secondes = nb_s - heures*3600 - minutes*60;
  // char a_afficher[8]="00:00:00";
  // a_afficher[0] = (char)(heures/10 + '0');
  // a_afficher[1] = (char)(heures%10 + '0');
  // a_afficher[3] = (char)(minutes/10 + '0');
  // a_afficher[4] = (char)(minutes%10 + '0');
  // a_afficher[6] = (char)(secondes/10 + '0');
  // a_afficher[7] = (char)(secondes%10 + '0');
  //
  // affichage_haut_droite(8, a_afficher);
  affichage_haut_droite(4, "test");
}


// void reglage_frequence(void){
//   outb(0x34, 0x43);
//   outb( (QUARTZ / CLOCK_FREQ) % 256, 0x40); // envoi des 8 bits de poids faible
//
//   uint8_t nv_freq_poids_fort = (QUARTZ / CLOCK_FREQ) >> 8;
//   outb(nv_freq_poids_fort, 0x40); // envoi des 8 bits de poids fort
// }

void reglage_frequence(){
  outb(0x34, 0x43);
  uint8_t faible = ((0x1234DD / 50)%256);
  uint8_t fort = ((0x1234DD / 50)/256);
  outb(faible, 0x40);
  outb(fort, 0x40);
}

void init_traitant_IT(int32_t num_IT, void (*traitant)(void)){
    /*On récupère l'adresse à laquelle il faut écrire dans la table des interruptions*/
    uint32_t *vect = (uint32_t *) (0x1000 +2*sizeof(uint32_t)*num_IT);

    /**Premier mot de l'entrée : cste KERNEL_CS (dans segment.h) puis 16 bits de poids faible de l'adresse du traitant*/
    *vect = ((uint32_t)traitant & 0x0000FFFF) | (KERNEL_CS<<16);

    /*Deuxième mot : 16 bits de poids fort de l'adresse du traitant et cste 0x8E00*/
    *(vect+1) = 0x8E00 | ((uint32_t)traitant & 0xFFFF0000);
}


void masque_IRQ(uint32_t num_IRQ, bool masque) {
    uint8_t inbut = inb(0x21);
    inbut &= (1<<num_IRQ) ^0xFF;
    inbut |= masque << num_IRQ;
    outb(inbut, 0x21);
}
