package travail;

import java.util.SortedSet;

/**
 * Stratégie élémentaire un peu plus poussée:
 * le chef pompier assigne une tache aux trois robots en meme temps
 * au lieu d'un seul robot qui fait toutes les taches.
 * 
 * Le chef pompier a une vision d'ensemble de la situation:
 * il connait l'intégralité de la carte
 * la position des incendies
 * peut interroger les robots
 * decide de l'affectation des robots
 */
public class ChefPompierTousRobots {
	private DonneesSimulation donnees; // contient la carte, les incendies et les robots
	private SortedSet<Evenement> evenements; // contient la liste des evenements
	private Simulateur sim;
	//private long dateDepart;

	public ChefPompierTousRobots(DonneesSimulation donnees, SortedSet<Evenement> evenements, Simulateur sim) {
		this.donnees = donnees;
		this.evenements = evenements;
		//this.dateDepart = date;
		this.sim = sim;
	}
	/**
	 * strategie qui consiste a envoyer des robots intervenir
	 * sur des incendies, sans recherche d'optimisation
	 */
	public void strategieTousRobots() {
		Robot robot;
		int indiceRobot;
		boolean cheminExiste;
		// Initialisation 
		// on definit des objets temporaires pour le calcul des evenements
		int incendies[] = new int[donnees.getNbIncendies()];
		long datesRobots[] = new long[donnees.getNbRobots()];
		Case posRobots[] = new Case[donnees.getNbRobots()];
		//boolean estOccupe[]  = new boolean[donnees.getNbRobots()];		
		initDonneesIncendies(incendies, donnees.getIncendie());
		initDonneesRobots(datesRobots, posRobots, donnees.getRobot(), sim.getDate());
		
		// debut strategie
		indiceRobot = 0;
		// on parcours les incendies
		for(Incendie incendie : donnees.getIncendie()) {
		// pour chaque incendie, on cherche un robot non occupé
			robot = donnees.getRobot()[indiceRobot];
			while(indiceRobot< donnees.getNbRobots() && (this.sim.getDate() < datesRobots[indiceRobot] || robot.getReservoir() == 0)) {
				 indiceRobot++;
				if(indiceRobot == donnees.getNbRobots()) {	
					break; // on termine la simulation dans ce cas
				}
				 robot = donnees.getRobot()[indiceRobot];
			}
			if(indiceRobot == donnees.getNbRobots()) {	
				break; // on termine la simulation dans ce cas
			}
		
		// on calcule le plus court chemin pour ce robot
			CalculeChemin chemin = new CalculeChemin(robot);
			cheminExiste = chemin.CalculeRoute(posRobots[indiceRobot], incendie.getPosition(), donnees.getCarte());
		// on deplace le robot si le chemin existe
			if (cheminExiste) {
				
				datesRobots[indiceRobot] += chemin.calculDeplacements(datesRobots[indiceRobot], evenements, donnees.getCarte().getTailleCases());
				
		// arrivé sur place, le robot verse son eau
				posRobots[indiceRobot] = incendie.getPosition();
				long tempsVersement = robot.tempsVerserEau(Math.min(incendie.getLitresEteindre(), robot.getReservoir()));
				evenements.add(new EvenementVerserEau(datesRobots[indiceRobot], robot, incendie.getLitresEteindre()));
				
				datesRobots[indiceRobot] += tempsVersement;
			} else {
	 			// on cherche un autre robot
				indiceRobot ++;
			}	
		}
	}
	
	
	/**
	 * Initialise le Tableau incendiesTmp avec les Incendies de la cartes  
	 * @param incendiesTmp
	 * @param incendies
	 */
	
	private void initDonneesIncendies(int[] incendiesTmp, Incendie[] incendies) {
		for(int i=0; i<incendies.length; i++) {
			incendiesTmp[i] = incendies[i].getLitresEteindre();
		}
	}
	
	/**
	 * Initialiser les position temporaire des robot avec les positions actuelle des robot dans la carte ainsi que les temps du robot au fur et a mesure de la programation des evenement 
	 * @param datesRobotsTmp
	 * @param posRobotsTmp
	 * @param robots
	 * @param dateDepart
	 */
	private void initDonneesRobots(long[] datesRobotsTmp, Case[] posRobotsTmp, Robot[] robots, long dateDepart) {
		for(int i=0; i<robots.length; i++) {
			datesRobotsTmp[i] = dateDepart;
			posRobotsTmp[i] = robots[i].getPosition();
		}
	}
	
	
}
