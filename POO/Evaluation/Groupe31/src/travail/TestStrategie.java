package travail;

import java.util.SortedSet;

import Enum.TypeRobot;

public class TestStrategie {
	
	private DonneesSimulation donnees; // contient la carte, les incendies et les robots
	private SortedSet<Evenement> evenements; // contient la liste des evenements
	
	private Simulateur sim;

	public TestStrategie(DonneesSimulation donnees, SortedSet<Evenement> evenements, Simulateur sim) {
		this.donnees = donnees;
		this.evenements = evenements;
		this.sim = sim;
	}
	/**
	 * strategie qui consiste a envoyer des robots intervenir
	 * sur des incendies, sans recherche d'optimisation
	 * @throws InterruptedException 
	 */

	public void strategieEvoluee() {
		
		
		
		int incendies[] = new int[donnees.getNbIncendies()];
		long datesRobots[] = new long[donnees.getNbRobots()];
		Case posRobots[] = new Case[donnees.getNbRobots()];
		
		int indiceRobotAAffecter = 0;
		initDonneesIncendies(incendies, donnees.getIncendie());
		initDonneesRobots(datesRobots, posRobots, donnees.getRobot(), sim.getDate());
		
	
		Incendie incendie;

		long tempsDeplacement;
		long tempsDeplacementMin = Long.MAX_VALUE;
		CalculeChemin cheminAAjouter = null;
		boolean cheminExiste ;
		boolean dejaAffecter[] = new boolean[donnees.getNbIncendies()];

		int i = trouverIncendie(donnees.getIncendie(), dejaAffecter);
		
		while(i != -1 ) {
			incendie = donnees.getIncendie()[i];
			tempsDeplacementMin = Long.MAX_VALUE;
			cheminAAjouter = null;
			for(int indiceRobot = 0; indiceRobot < donnees.getNbRobots(); indiceRobot++) {
				Robot robot = donnees.getRobot()[indiceRobot];
				if(robot.getDateFinTache() < sim.getDate()) {

					CalculeChemin chemin = new CalculeChemin(robot);

					// calcul de chemin optimal et rajout des evenements elementaires dans la liste d'evt
					
					cheminExiste = chemin.CalculeRoute(posRobots[indiceRobot], incendie.getPosition(), donnees.getCarte());
					if(cheminExiste) {
						tempsDeplacement = chemin.calculDeplacements(sim.getDate(), donnees.getCarte().getTailleCases());
						if(tempsDeplacement < tempsDeplacementMin) {
							tempsDeplacementMin = tempsDeplacement;
							cheminAAjouter = chemin;
							indiceRobotAAffecter = indiceRobot;
						}
					}
			 		
				}
			}
			if(cheminAAjouter != null) {

				
				if(cheminAAjouter.getRobot().getType() == TypeRobot.Drone) {
					System.out.println(cheminAAjouter.getDeplacementSequence());
				}
				long dateVerserEau = cheminAAjouter.calculDeplacements(sim.getDate(), evenements, donnees.getCarte().getTailleCases());
				evenements.add(new EvenementVerserEau(dateVerserEau, cheminAAjouter.getRobot(), incendie.getLitresEteindre()));
				posRobots[indiceRobotAAffecter] = incendie.getPosition();
				System.out.print(posRobots[0]);
				
				
				
			}
			else {
				dejaAffecter[i] = false;
				//date = AffecterUneDate(donnees.getRobot()) +1 ;	
				//System.out.println(date);
			}
			
			i = trouverIncendie(donnees.getIncendie(),dejaAffecter);
		}

	}
	/**
	 * 
	 * 
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
	private int trouverIncendie(Incendie[] incendies, boolean dejaAffecter[]) {
		for(int i=0; i<incendies.length; i++) {
		//	System.out.println("incendie  = " + incendies[i] + " deja affecter = "+  dejaAffecter[i] + incendies[i].getLitresEteindre());
			if(incendies[i].getLitresEteindre() != 0 && !dejaAffecter[i]) {
				dejaAffecter[i] = true;
				return i;
				
			}
		}
		return -1;
	}
	
	private void initDonneesIncendies(int[] incendiesTmp, Incendie[] incendies) {
		for(int i=0; i<incendies.length; i++) {
			incendiesTmp[i] = incendies[i].getLitresEteindre();
		}
	}
	private void initDonneesRobots(long[] datesRobotsTmp, Case[] posRobotsTmp, Robot[] robots, long dateDepart) {
		for(int i=0; i<robots.length; i++) {
			datesRobotsTmp[i] = dateDepart;
			posRobotsTmp[i] = robots[i].getPosition();
		}
	}
}
