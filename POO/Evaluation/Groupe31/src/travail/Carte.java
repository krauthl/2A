package travail;

import java.util.ArrayList;


import Enum.*;
/**
 * Classe servant à définir une carte.
 * La carte contient des cases de taille tailleCase, ainsi
 * qu'un certain nombre de lignes et de colonnes.
 * La carte contient des méthodes permettant de calculer le
 * voisin d'une case, de vérifier si une case est valide etc.
 */
public class Carte {

    private int tailleCase, nbLignes, nbColonnes;
    private Case matrice[][];
    private ArrayList<Case> caseEau; // les cases contenant de l'eau dans la carte 

    public Carte(int nbLignes, int nbColonnes, int tailleCase) {
    	
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.tailleCase = tailleCase;
        this.matrice = new Case[this.nbLignes][this.nbColonnes];
        this.caseEau = new ArrayList<Case>();
    }

    public Carte() {
    	this.nbLignes = 0;
    	this.nbColonnes = 0;
    	this.tailleCase = 0;
    }

    
    public int getNbLignes() {
      return this.nbLignes;
    }

    public int getNbColonnes() {
      return this.nbColonnes;
    }

    public int getTailleCases() {
      return this.tailleCase;
    }

    public Case getCase(int lig, int col) {
      // TODO faut peut être rajouter des exceptions
      return this.matrice[lig][col];
    }

    public void setCase(int i, int j, Case uneCase) {
      this.matrice[i][j] = uneCase;
    }
    
    public ArrayList<Case> getCaseEau() {
    	return this.caseEau;
    }
    
    /**
     * Verifie selon la structure de la carte si le voisin existe 
     * @param src
     * @param dir
     * @return True si le voisin existe 
     */

    public boolean voisinExiste(Case src, Direction dir) {
      switch(dir) {
        case NORD:
          return (src.getLigne()>0);

        case SUD:
          return (src.getLigne()<this.getNbLignes()-1);

        case EST:
          return (src.getColonne()<this.getNbColonnes()-1);

        case OUEST:
          return (src.getColonne()>0);

        // cas où la direction est fausse
        default:
          return false;

      }
    }
    
    /**
     * Incerer une case d'eau a la position taille + 1 du tableau CaseEau
     * @param caseEau : Position de la case Eau 
     * @throws Exception 
     */
    
    public void inserCaseEau(Case caseEau) throws Exception {
    	if(caseEau.getNature() != NatureTerrain.EAU) {
    		throw new Exception(" la Case en parametre n'est pas une case Eau");
    	}
    	this.caseEau.add(caseEau);
    }
    
    
    
    /**
     * Renvoie la Case Voisin D'une case donne dans une direction donne
     * @param src : La case pour la quelle on veut trouvé le voisin
     * @param dir : la direction du voisin a cherché
     * @return
     */
    public Case getVoisin(Case src, Direction dir) {
        if (voisinExiste(src, dir)) {
          switch(dir) {

      			case NORD:
      					return this.matrice[src.getLigne()-1][src.getColonne()];


      			case SUD:
      					return this.matrice[src.getLigne()+1][src.getColonne()];


      			case EST:
      					return this.matrice[src.getLigne()][src.getColonne()+1];


      			case OUEST:
      					return this.matrice[src.getLigne()][src.getColonne()-1];

      		}
        }
          //si le voisin n'existe pas ....
          return null;
    }
    
    
    /**
     * Fonction qui verifie si la Case est dans la configurationde la Carte
     * @param maCase
     * @return True si la case appartient a la Carte, false sinon
     */
    public Boolean verifeCase(Case maCase) {
    	if(maCase.getLigne()<=(this.nbLignes-1) && maCase.getColonne()<=(this.nbColonnes-1)
    			&& maCase.getLigne()>=0 && maCase.getColonne()>= 0 ) {
    		return true;
    	}
    	
    	return false;
    }
    

    
  

    /**
     * cree une liste contenant les positons des cases contenant de l'eau
     * 
     */
    public void remplirCasesEau() {
    	for(int i=0; i<this.nbLignes; i++) {
    		for(int j=0; j<this.nbColonnes; j++) {
    			if(this.matrice[i][j].getNature() == NatureTerrain.EAU)
    				this.caseEau.add(this.matrice[i][j]);
    		}
    	}
    }
   
    /**
     * Fonction revoie true si les deux Case son Voisine dans la carte 
     * @param position : Case 1 
     * @param position2 : case 2 
     * @return boolean  
     */
	public boolean sonVoisin(Case position, Case position2) {

		if((Math.abs((position.getLigne() - position2.getLigne())) == 1
			&& Math.abs((position.getColonne() - position2.getColonne())) == 0) || 
				(Math.abs((position.getColonne() - position2.getColonne())) == 1 
			&& Math.abs((position.getLigne() - position2.getLigne())) == 0)) {

			return true;
		}
		return false;
	}
	
	  @Override
	    public String toString() {
	    	String carte = "carte "+nbColonnes+"x"+nbLignes+"; taille des cases = "
	    					+this.tailleCase+"\n";
	    	for(int i=0 ; i<this.nbLignes; i++) {
	    		for (int j = 0; j <this.nbColonnes ; j++) {
					carte += this.matrice[i][j].toString(); //ajout de toString de chaque case pour avoir une chaine de charactères
				}
	    	}
	    	return carte;
	    }
}
