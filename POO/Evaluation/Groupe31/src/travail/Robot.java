package travail;

import Enum.TypeRobot;
/**
  * Classe abstraite definissant un robot
  * il contient une position dans la carte, une vitesse et un reservoir.
  * Les robots peuvent se deplacer, deverser de l'eau sur un incendie
  * et remplir leur reservoir
  */

/* Pour les Class qui Herite des robot
	* TOUT  : il se deplace sur tout les terrains 				 	-> pour drone
	* LH	: il se deplace sur TerrainLibre(L) et Habitat (H) 		-> pour roues
	* LFH	: il se deplace sur TerrainLibre(L) et Habitat (H) et Foret ( F)   ->chenilles
	* LFRH	: il se deplace sur tout sauf l'eau 					-> pattes
	*/


public abstract class Robot {

	/** position du robot dans la carte */
    private Case position;

    private double vitesse;
    /** un robot dispose d'un reservoir d'eau
      * lorsqu'il est vide, il doit aller se remplir
      */
    private int reservoir;

    /* Chaque Robot a un type bien Precis
     * 4 type possible decrit dans le type enum Type
     */
    private TypeRobot type;



    
    /*  Chaque robot est affecte a une tache (deplacement , remplir reservoir , ou verseau eau) 
     * si c'est le cas alors Occupé = true 
     * false , sinon
     */
    private Boolean occupe;
    
    private long dateCourante; // date courante du robot
    
    /* indique la date de fin de tache actuelle pour le robot si il est occuper 
     * dans le cas ou il n'est pas occuper  la date est egal a 0 ou inferieur a la date actuelle de la simulation
     */
    private long dateFinTache;

	public long getDateFinTache() {
		return dateFinTache;
	}

	public void setDateFinTache(long dateFinTache) {
		this.dateFinTache = dateFinTache;
	}
	
	public long getDateCourante() {
		return dateCourante;
	}

	public void setDateCourante(long dateFinTache) {
		this.dateCourante = dateFinTache;
	}


  public Boolean estOccupe() {
		return occupe;

	}

	public void setOccupe(Boolean occuper) {
		this.occupe = occuper;
	}



	public TypeRobot getType() {
		return type;
	}

	public void setType(TypeRobot type) {
		this.type = type;
	}

  public int getReservoir(){
    return this.reservoir;
  }

	public Case getPosition() {
      return this.position;
  }

  public void setPosition(Case position) {
      this.position = position;
  }

  public double getVitesse() {
      return this.vitesse;
  }
  
  public void setVitesse(double vitesse) {
    this.vitesse = vitesse;
  }
  /**
   * cette methode modifie la vitesse du robot
   * selon le terrain où il se deplace
   */
  public abstract void setVitesse();
  
/**
 * versement d'eau sur une case indice qui a besoin de quantite litres d'eau
 * pour s'eteindre
 * @param quantite
 * @return quantite d'eau necessaire restante pour eteindre le feu 
 */
  public int deverserEau(int quantite){
	if(this.type != TypeRobot.Pattes) {  
	    if(quantite > this.reservoir) {
	    	int tmp = this.reservoir;
	    	this.reservoir = 0;
	    	return quantite - tmp;
	    } else {
	    	this.reservoir -= quantite;
	    	return 0;
	    }
	}else {
		return 0;
	}
  }
 
  /**
   * Selon le type de robot, remplit le reservoir avec litresEau d'eau
   * @param litresEau
   */
  public void remplirReservoir(int litresEau) {

    // FIXME ? Il faut prendre en considération le temps et lieu de
    // remplissage voir annexe B.2?
	  if(this.type != TypeRobot.Pattes) {
		  this.reservoir = litresEau;
	  }
	  else {
		  this.reservoir = Integer.MAX_VALUE;
	  }
	  
  }
  
  /**
   * Verifie si le robot a le droit de ce deplacer vars la caseDestination
   * @param carte
   * @param caseDestination
   * @return Boolean Tru esi le robot peut acceder a la case
   */
  abstract public Boolean deplacementAutoriser(Carte carte, Case caseDestination);
  
  /**
   * renvoie le temps pour qu'un robot puisse remplire sont reservoir
   * @return long 
   */
  abstract public long tempsRemplissage();
  
  /**
   * Fonction qui Calcule le temps necessaire pour verser litreEau par le robot 
   * @param litresEau : nombre de litre a verser
   * @return
   */
  abstract public long tempsVerserEau(int litresEau);
  
  
  /**
   * Fonction qui renvoie la capacité du reservoir 
   * @return int
   */
  abstract public int capaciteReservoir();
  
  @Override
  public String toString() {
    return "Robot de type " + this.getType() + " dans la case " +
          this.getPosition().toString() + " possédant un réservoir rempli à " +
          this.getReservoir() + "L et roulant à " + this.getVitesse() +" Km/H\n ";
  }
  
  
	/**
	 * calcule le temps de deplacement nécessaire à un robot pour se rendre
	 * sur une case voisine donnée 
	 * @return le temps de deplacement
	 */
	public double tempsDeplacementVoisin (int tailleCase) {
		// calcul vitesse du robot: la vitesse du robot change selon les terrains
		this.setVitesse();
		return (tailleCase/(this.getVitesse()/3.6))*1000;
	}

@Override
public boolean equals(Object obj) {
	if(this.getPosition().equals(((Robot)obj).getPosition())) {
		if(this.getType() == ((Robot)obj).getType()) {
			return true;
		}
		return false;
	}
	return false;
}
}
