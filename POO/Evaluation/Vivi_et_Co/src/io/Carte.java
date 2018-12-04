package io;

/**
* Contient les donnees de la carte
*/
public class Carte{

     private int taille_cases;
     private int nb_lignes;
     private int nb_colonnes;
     private Case[] tableau_de_cases;

     /**
     Cree une carte de taille nb_lignes*nb_colonnes dont les cases sont de
     taille taille_cases
     */
     public Carte(int taille_cases, int nb_lignes, int nb_colonnes, Case[] tableau_de_cases){
       this.setTailleCases(taille_cases);
       this.setNbLignes(nb_lignes);
       this.setNbColonnes(nb_colonnes);
       this.setTableauDeCases(tableau_de_cases);
     }

     /**
     * Definit toutes les case
     * Invariant de classes : len(tableau) == nbLignes * nbColonnes
     */
     public void setTableauDeCases(Case[] tab){
          if (tab.length != this.getNbLignes() * this.getNbColonnes() ){
              throw new IllegalArgumentException("Nombre de cases non conforme !");
          }
          this.tableau_de_cases = tab;
     }

     public Case[] getTableauDeCases(){
       return this.tableau_de_cases;
     }

     /* Definit la taille des cases.
     Invariant de classe : taille > 0
     */
     public void setTailleCases(int taille){
         if (taille <= 0){
           throw new IllegalArgumentException("Taille des cases nulle ou negative !");
         }
         this.taille_cases = taille;
     }

     /**
     * returns the value of TailleCases
     */
     public int getTailleCases(){
          return this.taille_cases;
      }



      /* Definit le nombre de lignes.
      Invariant de classe : NbLignes > 0
      */
     public void setNbLignes(int nbl){
         if (nbl <= 0){
           throw new IllegalArgumentException("Nombre de lignes nul ou negatif !");
         }
         this.nb_lignes = nbl;
     }

     /**
     * returns the value off NbLignes
     */
     public int getNbLignes(){
          return this.nb_lignes;
      }

      /* Definit le nombre de Colonnes.
      Invariant de classe : NbColonnes > 0
      */
     public void setNbColonnes(int nbc){
         if (nbc <= 0){
           throw new IllegalArgumentException("Nombre de Colonnes nul ou negatif !");
         }
         this.nb_colonnes = nbc;
     }

     /**
     * returns the value of NbColonnes
     */
     public int getNbColonnes(){
          return this.nb_colonnes;
      }

      /**
      * Renvoie true s'il a un voisin dans la direction donnee, false sinon.
      */
      public boolean voisinExiste(Case C, Direction direction){
            switch(direction){
              /*Fais un case sur la direction
              On verifie qu'on ne depasse pas */
                case NORD:
                  if (C.getLigne() -1 >= 0){
                    return true;
                  }
                  else{
                    return false;
                  }


                case EST:
                  if (C.getColonne() +1 < this.getNbColonnes()){
                    return true;
                  }
                  else{
                    return false;
                  }


                case SUD:
                  if (C.getLigne() +1 < this.getNbLignes()){
                    return true;
                  }
                  else{
                    return false;
                  }

                case OUEST:
                  if (C.getColonne() -1 >= 0){
                    return true;
                    }
                  else{
                    return false;
                  }
                default:
                  return false;

            }

       }

       /**
       * Renvoie le voisin de la case C dans la direction donnee
       */
       public Case getVoisin(Case C, Direction direction){
            if (voisinExiste(C, direction)){

              /*On verifie quand même que ce voisin existe*/
              int ligne = C.getLigne();
              int colonne = C.getColonne();
              Case[] Tableau = this.getTableauDeCases();
              int taille = this.getNbColonnes();

              switch(direction){

                case NORD:
                  return Tableau[taille*(ligne-1) + colonne];

                case EST:
                  return Tableau[taille*(ligne) + colonne+1];

                case SUD:
                  return Tableau[taille*(ligne+1) + colonne];

                case OUEST:
                  return Tableau[taille*(ligne) + colonne -1];
                default:
                  return C;
            }
          } else {
            return null;
          }
       }









      public String ToString(){
         String returned_string =  "Taille des cases : " + this.getTailleCases() + "\n" + "Dimensions : " + this.getNbLignes() + " * " + this.getNbColonnes();
         for (int lig=0; lig < this.getNbLignes(); lig++){
             for (int col=0; col < this.getNbColonnes(); col++){
                 returned_string += "\n Case(" + lig + "," + col + "): " + this.tableau_de_cases[lig*this.getNbColonnes()+col].getNature();
             }
         }
         return returned_string;
     }

}
