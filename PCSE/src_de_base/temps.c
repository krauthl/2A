#include <cpu.h>
#include <inttypes.h>
#include <stddef.h>
#include <string.h>
#include <stdio.h>
#include <stdbool.h>
#include "gestion_ecran.h" //A MODIFIER
#include "temps.h"
#include "segment.h"
#define QUARTZ 0x1234DD
#define CLOCK_FREQ 50
//
// static int nb_tic = 0; //Nombre de "tics"
// static int nb_s = 0; //Nombre de secondes : 1 seconde pour 50 tics


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

  // //On compte les tics de l'horloge et on ajoute une seconde tous les 50 tics
  // nb_tic++;
  // if (nb_tic%50==0){
  //   nb_s++;
  // }
  //
  // //Afficher l'heure
  // int heures = nb_s/3600;
  // int minutes = (nb_s - heures*3600)/60;
  // int secondes = nb_s - heures*3600 - minutes*60;
  // // int heures_format = sprintf("02%u", heures); //complète avec des 0 jusqu'à avoir 2 caractères
  // // char minutes_format = sprintf("02u", minutes);
  // // char secondes_format = sprintf("02u", secondes);
  // char a_afficher[8]="00:00:00";
  // //a_afficher[0] = (char)heures/10;
  // a_afficher[0] = (char)(heures/10 + '0');
  // a_afficher[1] = (char)(heures%10 + '0');
  // a_afficher[3] = (char)(minutes/10 + '0');
  // a_afficher[4] = (char)(minutes%10 + '0');
  // a_afficher[6] = (char)(secondes/10 + '0');
  // a_afficher[7] = (char)(secondes%10 + '0');
  //
  // affichage_haut_droite(8, a_afficher);

  printf("coucou\n");
}


void reglage_frequence(void){
  outb(0x34, 0x43);
  outb( (QUARTZ / CLOCK_FREQ) % 256, 0x40); // envoi des 8 bits de poids faible

  uint8_t nv_freq_poids_fort = (QUARTZ / CLOCK_FREQ) >> 8;
  outb(nv_freq_poids_fort, 0x40); // envoi des 8 bits de poids fort

}

//
// void init_traitant_IT(int32_t num_IT, void (*traitant)(void)) {
//   uint32_t * table = (uint32_t *) 0x1000;
//   uint32_t first_word = KERNEL_CS << 16;
//   first_word |= ((uint32_t) traitant & 0x0000FFFF);
//   uint32_t second_word = (uint32_t) traitant;
//   second_word &= 0xFFFF0000;
//   second_word |= 0x8E00;
//   *(table + num_IT*2) = first_word;
//   *(table + num_IT*2 + 1) = second_word;
// }

void init_traitant_IT(int32_t num_IT, void (*traitant)(void)){
    uint32_t word = ((KERNEL_CS << 16) | (((uint32_t)traitant) & 0xFFFF));
    uint32_t *addr = (uint32_t*)(0x1000 + num_IT * 8);
    *addr = word;
    //memset((void*)(0x1000 + (num_IT-1)), word, sizeof(word));
    word = (((uint32_t)traitant) & 0xFFFF0000) | 0x8E00;
    addr++;
    *addr = word;
    // memset((void*)(0x1000 + (num_IT-1) + 32), word, sizeof(word));

}


void masque_IRQ(uint32_t num_IRQ, bool masque) {
    uint8_t inbut = inb(0x21);
    inbut &= (1<<num_IRQ) ^0xFF;
    inbut |= masque << num_IRQ;
    outb(inbut, 0x21);
}
