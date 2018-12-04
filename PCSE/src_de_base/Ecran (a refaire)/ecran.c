#include <cpu.h>
#include <inttypes.h>
#include <stddef.h>
#include <string.h>
#include "ecran.h"

uint16_t *ptr_mem(uint32_t lig, uint32_t col)
{
  //Renvoie un pointeur vers la case mémoire correspondant aux coordonnées
  //fournies
  return (uint16_t *)(0xB8000 + (lig*80 + col)*2);
}

void ecrit_car(uint32_t lig, uint32_t col, char c)
{
  //Ecrit le caractère c aux coordonnées spécifiées
  //Paramètres à ajouter pour préciser couleur de caractère, couleur de fond, bit de clignotement
  uint16_t *ptr = ptr_mem(lig, col);
  *ptr = c;
  //*ptr = *ptr + 0x3000; //texte noir sur fond cyan
  *ptr = *ptr + 0x0F00; //texte blanc sur fond noir
}

void efface_ecran(void)
{
  //Parcourt les lignes et les colonnes de l'écran pour écrire dans
  //chaque case un espace en blanc sur fond noir
  for (uint16_t ligne = 0; ligne<25; ligne++){
    for (uint16_t colonne = 0; colonne<79; colonne++){
      ecrit_car(ligne, colonne, ' ');
    }
  }
}

void place_curseur(uint32_t lig, uint32_t col)
{
  //Place le curseur à la position donnée

  //Calcul de la position du curseur
  uint16_t pos = col + lig*80;

  //Définition des ports
  uint16_t port_commande = 0x3D4;
  uint16_t port_donnees = 0x3D5;
  //Envoi de la commande 0x0F sur le port de commande pour signaler
  //qu'on envoie la partie basse de la position du curseur
  outb(0x0F, port_commande);

  //Envoi de la partie basse sur le port de donnees
  uint8_t partie_basse = pos & 0x00FF;
  outb(partie_basse, port_donnees);

  //Envoi de la commande 0xOE sur le port de commande pour signaler
  //qu'on envoie la partie haute
  outb(0x0E, port_commande);

  //Envoi de la partie haute de la position sur le port de données
  uint8_t partie_haute = (pos & 0xFF00)>>8;
  outb(partie_haute, port_donnees);
}
