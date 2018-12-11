#include <cpu.h>
#include <inttypes.h>
#include <string.h>
#include "ecran.h"
#include "temps.h"

uint16_t LIGNE_CURSEUR = 0;
uint16_t COLONNE_CURSEUR = 0;
bool horloge;

uint16_t *ptr_mem(uint32_t lig, uint32_t col){
    /*Renvoie un pointeur sur la case mémoire correspondant aux coordonnées fournies*/
    return (uint16_t*) (0xB8000 + 2 * (col + 80*lig));
}

void ecrit_car(uint32_t lig, uint32_t col, char c){
    /*Ecrit le caractère c aux coordonnées voulues*/
    uint16_t *adresse = ptr_mem(lig, col);
    *adresse = c; //Ecrit c à l'adresse voulue
    *adresse += 0x0F00; //Pour écrire un caractère blanc sur fond noir
}

void efface_ecran(void){
    /*Efface l'écran en imprimant un caractère espace sur toutes les cases*/
    for (int ligne = 0; ligne <25; ligne++){
        for (int colonne = 0; colonne<80; colonne++){
            ecrit_car(ligne, colonne, ' ');
        }
    }
    place_curseur(0, 0);
    LIGNE_CURSEUR = 0;
    COLONNE_CURSEUR = 0;
}

void place_curseur(uint32_t lig, uint32_t col){
    /*Place le curseur à la position voulue*/
    /*Port de commandes : 0x3D4
      Port de données :  0x3D5*/
    uint16_t pos = col+lig*80;
    LIGNE_CURSEUR = lig;
    COLONNE_CURSEUR = col;

    //Traitement partie basse
    outb(0x0F, 0x3D4);
    uint8_t partie_basse_position = pos & (0x00FF);
    outb(partie_basse_position, 0x3D5);

    //Traitement partie haute
    outb(0x0E, 0x3D4);
    //uint8_t partie_haute_position = pos & (0xFF00)>>8;
    uint8_t partie_haute_position = pos >>8;
    outb(partie_haute_position, 0x3D5);
}

void traite_car(char c){
    /* Si c est un caractère normal : affichage
       Si c est un caractère spécial : on implante l'effet voulu*/
    if ((32 <= c) && (c <= 126)){
        if (COLONNE_CURSEUR == 79){
            ecrit_car(LIGNE_CURSEUR, COLONNE_CURSEUR, c);
            if (LIGNE_CURSEUR == 24){
                defilement();
                place_curseur(24, 0);
            } else {
                place_curseur(LIGNE_CURSEUR +1, 0);
            }
        } else {
            ecrit_car(LIGNE_CURSEUR, COLONNE_CURSEUR, c);
            place_curseur(LIGNE_CURSEUR, COLONNE_CURSEUR+1);
        }
    } else {
        switch(c){
            case '\b' :
                /*Recule curseur d'une colonne si colonne!=0*/
                if (COLONNE_CURSEUR!=0){
                    place_curseur(LIGNE_CURSEUR, COLONNE_CURSEUR-1);
                }
                break;
            case '\t' :
                /*Avance à la prochaine tabulation (colonnes 0, 8, 16, ..., 72, 79)*/
                place_curseur(LIGNE_CURSEUR, (COLONNE_CURSEUR/8 + 1)*8);
                break;
            case '\n' :
                /*Déplace curseur sur la ligne suivante, colonne 0*/
                if (LIGNE_CURSEUR == 24){
                    defilement();
                    place_curseur(24, 0);
                } else {
                    place_curseur(LIGNE_CURSEUR+1, 0);
                }
                break;
            case '\f' :
                /*Efface l'écran et place le curseur en 0, 0*/
                efface_ecran();
                break;
            case '\r' :
                /*Déplace le curseur sur la ligne actuelle, colonne 0*/
                place_curseur(LIGNE_CURSEUR, 0);
                break;
            default :
                break;
        }
    }
}

void defilement(void){
    /*Fait remonter d'une ligne l'affichage à l'écran*/
    for (int i = 0; i<24; i++){
        if (i==0 && horloge){
            //Cas où il ne faut pas effacer l'horloge!
            memmove(ptr_mem(i,0), ptr_mem(i+1, 0), 65*2);
        } else {
            memmove(ptr_mem(i,0), ptr_mem(i+1,0), 80*2);
        }
    }
    //Efface la dernière ligne pour pas l'avoir en double :
    for (int i = 0; i<80; i++){
        ecrit_car(24, i, ' ');
    }
}

void console_putbytes(char *chaine, int32_t taille){
    /*Affiche une chaine de caractères à la position courante du curseur*/
    for (uint32_t i = 0; i<taille; i++){
        traite_car(*(chaine+i));
    }
}
