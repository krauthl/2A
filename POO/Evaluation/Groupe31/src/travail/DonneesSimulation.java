package travail;

/** La classe principale regroupant toutes les données du problème:
 *  une carte, les incendies et les robots 
 *  
 *  Elle contient egalement les donnees initiales du problemes
 *  afin de pouvoir y revenir lors du Restart:
 *  nbLitresEteindre et posInitRobot
 */

public class DonneesSimulation {
	
	  private Carte carte;
	  private Incendie[] incendies;
	  private Robot[] robot;
	
	  private int nbRobots;
	  private int nbIncendies;
	
	  private int nbLitresEteindre[]; // le nombre de litres d'eau pour eteindre chaque incendie
	  private Case posInitRobot[]; // la position initiale des robots
	   
	  /**
	   * utilisé lors du restart
	   * enregistre le nombre de litres d'eau nécessaires au départ
	   * pour chacune des incendies
	   */
	  public void setNbLitresEteindre() {
		  this.nbLitresEteindre = new int[this.incendies.length];
		  for(int i=0; i<this.incendies.length; i++) {
			  this.nbLitresEteindre[i] = this.incendies[i].getLitresEteindre();
		  }
	  }
	
	  public int[] getNbLitresEteindre() {
		  return this.nbLitresEteindre;
	  }
	  
	  /**
	   * utilisé lors du restart
	   * enregistre les positions initiales des robots
	   */
	  public void setPosInitRobot() {
		  this.posInitRobot = new Case[this.robot.length];
		  for(int i=0; i<this.robot.length; i++) {
			  this.posInitRobot[i] = this.robot[i].getPosition();
		  }
	  }
	  
	  public Case[] getPosInitRobot() {
		  return this.posInitRobot;
	  }
	  
	  public int getNbRobots() {
		  return nbRobots;
	  }
	  
	  public void setNbRobots(int nbRobots) {
		  this.nbRobots = nbRobots;
	  }
	  
	  public int getNbIncendies() {
		  return nbIncendies;
	  }
	  
	  public void setNbIncendies(int nbIncendies) {
		  this.nbIncendies = nbIncendies;
	  }

	  public Carte getCarte() {
		  return carte;
	  }

	  public void setCarte(Carte carte) {
		  this.carte = carte;
	  }

	  public Incendie[] getIncendie() {
			return this.incendies;
	  }

	  public void setIncendie(Incendie[] incendie) {
		  this.incendies = incendie;
	  }
	
	  public void setIncendie(Incendie incendie, int indice) {
		  this.incendies[indice] = incendie;
	  }
	
	  public Robot[] getRobot() {
		  return robot;
	  }

	  public void setRobot(Robot[] robot) {
		  this.robot = robot;
	  }
	
	  @Override
	  public String toString() {
		 String str =  "Affichage de la Carte \n" + this.carte.toString() + "\n"+ "Afichage des Robots \n";


		for(int i=0; i< this.nbRobots;i++) {
				str = str +  this.robot[i].toString() + "\n";
		}
		str = str + "Affichage des Incendies \n" ;
		for(int i=0; i< this.nbIncendies;i++) {
			str = str +  this.incendies[i].toString() + "\n";
		}
		return str ;
	  }
	/**
	 * on vérifie si une case donnée contient une incendie
	 * on renvoie l'indice correspondant à cette incendie
	 * 
	 * Dans le cas où il s'agit d'une case incendie déjà eteinte
	 * on ne renvoie pas l'indice de l'incendie 
	 * puisqu'elle n'existe plus
	 */
	  public int estCaseIncendie(Case c) {
    	// TODO verifier si la case en paramètre est valide (ie pas de val negatives)
    	for(int i=0; i< this.incendies.length; i++) {
    		if(c.equals(this.incendies[i].getPosition()) && this.incendies[i].getLitresEteindre()>0) {
    				return i;
    		}
    	}
    	return -1;
	  }
    
    /**
     * utilisé lors du reset pour remettre les incendies
     * leur affecte le nb de litres d'eau nécessaires au départ
     * pour eteindre une incendie
     */
	  public void resetIncendies() {
    	for(int i=0; i<this.incendies.length; i++) {
    		this.incendies[i].setLitresEteindre(this.nbLitresEteindre[i]);
    	}
	  }
    
    /**
     * utilisé lors du restart
     * remet les positions des robots
     * selon les positions initiales
     * ainsi que les reservoirs des robots à leur
     * capacité maximale
     */
    public void resetRobots() {
    	for(int i=0; i<this.robot.length; i++) {
    		this.robot[i].setPosition(this.posInitRobot[i]);
    		this.robot[i].remplirReservoir(this.robot[i].capaciteReservoir());
    		this.robot[i].setDateCourante(0);
    	}
    }
}
