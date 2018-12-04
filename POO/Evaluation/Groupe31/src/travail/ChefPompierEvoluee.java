package travail;

import java.util.ArrayList;
import java.util.SortedSet;


/**
 * Stratégie plus evoluée
 * le chef pompier assigne des taches aux robots non occupés
 * Les robots se deplacent vers les cases contenant de l'eau tout seuls
 * pour remplir leurs reservoirs lorsque cela est necessaire
 */

import Enum.TypeRobot;


public class ChefPompierEvoluee {
	private DonneesSimulation donnees; // contient la carte, les incendies et les robots
	private SortedSet<Evenement> evenements; // contient la liste des evenements
	private Simulateur sim;
	
	public ChefPompierEvoluee(DonneesSimulation donnees, Simulateur sim) {
		this.donnees = donnees;
		this.evenements = sim.getEvenements();
		this.sim = sim;
	}
	
	/**
	 * strategie qui consiste a envoyer des robots intervenir
	 * sur des incendies et ensuite remplir leurs reservoirs au besoin
	 * dans le but d'eteindre tous les incendies
	 */
	public void strategieEvoluee() {

		ArrayList<Case> casesEau = donnees.getCarte().getCaseEau();
	
		long date = sim.getDate();
		Incendie incendie;
		Robot robot;
		CalculeChemin cheminAAjouter;	
		incendie = trouverIncendie(this.donnees.getIncendie());
		
		while(incendie != null) {
			
			cheminAAjouter = null;
			
			// pour l'incendie en question, on cherche parmi les robots non occupés
			// le robot qui va le plus rapidement à la case de l'incendie
			robot = trouverRobot(incendie, donnees.getRobot(), date);
			
			if(robot != null) {
				
				
				cheminAAjouter = new CalculeChemin(robot);
				boolean cheminExiste = cheminAAjouter.CalculeRoute(robot.getPosition(), incendie.getPosition(), donnees.getCarte());
				assert cheminExiste==true;
				
				
				
				long dateVerserEau = cheminAAjouter.calculDeplacements(robot.getDateCourante(), evenements, donnees.getCarte().getTailleCases());
				assert robot.getReservoir() > 0;
				evenements.add(new EvenementVerserEau(dateVerserEau, robot, incendie.getLitresEteindre()));
				// mise a jour de la position du robot
				robot.setPosition(incendie.getPosition());
				// mise a jour de la date courante du robot
				robot.setDateCourante(robot.getDateFinTache());
				// mise a jour du reservoir du robot
				// mise a jour de la quantité d'eau restante pour l'incendie
				if(robot.getType() == TypeRobot.Pattes) {
					incendie.setLitresEteindre(0);
				}
				else {
					int resultat[] = calculDeverserEau(robot.getReservoir(), incendie.getLitresEteindre());
					robot.remplirReservoir(resultat[0]);
					incendie.setLitresEteindre(resultat[1]);
				}
				
				
				
				
				// on verifie si le reservoir du robot est vide 
				if(robot.getReservoir() == 0 ) {
				
					
				// dans ce cas on le fait aller se remplir vers la case d'eau la plus proche
					int indiceCaseEauAAffecter = trouverCaseEau(robot, casesEau, robot.getDateCourante());
				
					if(indiceCaseEauAAffecter >= 0) {
						
						Case caseEau = casesEau.get(indiceCaseEauAAffecter);
						// on verifie si le chemin n'est pas obligatoire Case Eau deja voisine
						if(! this.donnees.getCarte().sonVoisin(robot.getPosition(), caseEau) || robot.getType() == TypeRobot.Drone) {
							
						
						    cheminAAjouter = new CalculeChemin(robot);
							cheminExiste = cheminAAjouter.CalculeRoute(robot.getPosition(), caseEau, donnees.getCarte());
							
							
							
							
							assert cheminExiste==true;
							
							//mettre a jour la date du robot qui va remplir son reservoir
							cheminAAjouter.getRobot().setDateCourante(cheminAAjouter.getRobot().getDateFinTache());
							
							
						}
						// après avoir calcule le chemin vers la plus proche case d'eau
						// on rajoute les evenements Deplacer et Remplir dans la liste des evenements
						long dateRemplirReservoir= cheminAAjouter.calculDeplacements(robot.getDateCourante(), evenements, donnees.getCarte().getTailleCases());
						
						// mise à jour de la position du robot
						robot.setPosition(caseEau);
						// mise à jour de la date courante du robot
						robot.setDateCourante(robot.getDateFinTache());
						evenements.add(new EvenementRemplir(dateRemplirReservoir, robot));
						
						// mise à jour du reservoir du robot
						robot.remplirReservoir(robot.capaciteReservoir());
						// mise à jour de la date courante du robot
						robot.setDateCourante(robot.getDateFinTache());
					}
				}	
			}
			else {
				date = AffecterUneDate(donnees.getRobot()) +1 ;	
				
			}
			
			incendie = trouverIncendie(this.donnees.getIncendie());
		}
	
		
	}

	/**
	 * Affecte une nouvelle date au chef Pompier , cette fonction est utuliser dans le cas ou tout les robot sans affecter
	 * Ce qui laisse le chef pompier la possibilité de programmer des evenement a des date ulterieur
	 * @param la liste des robots dans la simulation : robots
	 * @return date 
	 * */
	private long AffecterUneDate(Robot[] robots) {
		long min = Long.MAX_VALUE;
		for(Robot robot : robots) {
			if(min > robot.getDateFinTache()) {
				min = robot.getDateFinTache();
			}
		}
		return min;
	}
	
    /**
     * La methode renvoie une incendie dans le cas où 
     * ils restent encore des incendies à eteindre,
     *  sinon elle renvoie null
     */
    public Incendie trouverIncendie(Incendie[] incendies) {
    	for(Incendie incendie : incendies) {
    		if(incendie.getLitresEteindre()>0)
    			return incendie;
    	}
    	return null; // tous les incendies ont été eteintes
    }
	
	 /**
	   * calcule le nb de litres restantes dans le reservoir du robot
	   * après le versement d'eau
	   * et renvoie le nb de litres d'eau restantes pour eteintre l'incendie
	   * @param reservoir
	   * @param quantite
	   * @return quantite d'eau necessaire restante pour eteindre le feu 
	   * @return litres d'eau restantes dans le reservoir du robot
	   */
	  public int[] calculDeverserEau(int reservoir, int quantite) {
		int retourner[] = new int[2]; // on retourne 2 valeurs (reservoir, incendie)
		if(quantite > reservoir) {
	    	retourner[0] = 0;
	    	retourner[1] = quantite - reservoir;
	    } else {
	    	retourner[0] = reservoir - quantite;
	    	retourner[1] = 0;
	    }  
	    return retourner;
	  }
	  
	  /**
	   * calcule du chemin du reservoir le plus proche au robot
	   * pour aller se remplir
	   */
	  private int trouverCaseEau(Robot robot, ArrayList<Case> casesEau, long date) {
		long tempsDeplacementMin = Long.MAX_VALUE;
		int indiceCaseEauAAffecter = -1; 
		for(int indiceEau=0; indiceEau< casesEau.size(); indiceEau++) {
			Case caseEau = casesEau.get(indiceEau);
			if(this.donnees.getCarte().sonVoisin(robot.getPosition(), caseEau)&& robot.getType() != TypeRobot.Drone) {
				
				return indiceEau;
			}
			CalculeChemin chemin = new CalculeChemin(robot);
			// posRobot = this.posRobots[indiceRobotAAffecter] 
			boolean cheminExiste = chemin.CalculeRoute(robot.getPosition(), caseEau, donnees.getCarte());
			
			if(cheminExiste) { 
				
				long tempsDeplacement = chemin.calculDeplacements(robot.getDateCourante(), donnees.getCarte().getTailleCases());
				
				if(tempsDeplacement < tempsDeplacementMin) {
					
					tempsDeplacementMin = tempsDeplacement;
					//cheminAAjouter = chemin;
					indiceCaseEauAAffecter = indiceEau;
				}
			}
		}
		return indiceCaseEauAAffecter;
	  }
	  /**
	   * trouve le robot qui peut aller le plus rapidement à l'incendie
	   */
	  private Robot trouverRobot(Incendie incendie, Robot[] robots, long date) {
		// robots = donnees.getRobot()
		long tempsDeplacement;
		long tempsDeplacementMin = Long.MAX_VALUE;
		Robot robotAAffecter = null;
		
		
		for(Robot robot : robots) {
			
			if(robot.getDateFinTache() < date) {
				
				CalculeChemin chemin = new CalculeChemin(robot);
	
				// calcul de chemin optimal et rajout des evenements elementaires dans la liste d'evt
				boolean cheminExiste = chemin.CalculeRoute(robot.getPosition(), incendie.getPosition(), donnees.getCarte());
				
				if(cheminExiste) {
					
					tempsDeplacement = chemin.calculDeplacements(date, donnees.getCarte().getTailleCases());
					if(tempsDeplacement < tempsDeplacementMin) {
						tempsDeplacementMin = tempsDeplacement;
						//cheminAAjouter = chemin;
						robotAAffecter = robot;
					}
				}
			}
		}
		
		return robotAAffecter;
	  }
}

