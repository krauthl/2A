package travail;

import java.util.SortedSet;

/**
 * le chef pompier a une vision d'ensemble de la situation:
 * il connait l'intégralité de la carte
 * la position des incendies
 * peut interroger les robots
 * decide de l'affectation des robots
 */
public class ChefPompierElementaire {
	private DonneesSimulation donnees; // contient la carte, les incendies et les robots
	private SortedSet<Evenement> evenements; // contient la liste des evenements
	private long dateDepart;

	public ChefPompierElementaire(DonneesSimulation donnees, SortedSet<Evenement> evenements, long date) {
		this.donnees = donnees;
		this.evenements = evenements;
		this.dateDepart = date;
	}
	
	
	/**
	 * strategie qui consiste a envoyer des robots intervenir
	 * sur des incendies, sans recherche d'optimisation
	 */
	public void strategieElementaire() {
		boolean cheminExiste;
		long dateCourante = this.dateDepart; // pour pouvoir associer les dates à chaque evenement
		int indiceRobot = 0;
		Robot robot;
		for(Incendie incendie : donnees.getIncendie()) {
			// affecter à un robot une incendie

			robot = donnees.getRobot()[indiceRobot];
			while(indiceRobot< donnees.getNbRobots() && (robot.estOccupe() || robot.getReservoir() == 0)) {
				 indiceRobot++;
				 robot = donnees.getRobot()[indiceRobot];
			}
			if(indiceRobot == donnees.getNbRobots())
				break; // on termine la simulation dans ce cas


				CalculeChemin chemin = new CalculeChemin(robot);
		 		// calcul de chemin optimal et rajout des evenements elementaires dans la liste d'evt
		 		cheminExiste = chemin.CalculeRoute(robot.getPosition(), incendie.getPosition(), donnees.getCarte());
		 		if (cheminExiste) {
		 			chemin.calculDeplacements(dateCourante, evenements, donnees.getCarte().getTailleCases());
		 			// arrivé sur place, le robot verse son eau
		 			dateCourante = robot.getDateFinTache();
		 			
			 		evenements.add(new EvenementVerserEau(dateCourante, robot, incendie.getLitresEteindre()));
			 		dateCourante = robot.getDateFinTache();
			 		
		 		}else {
		 			// on cherche un autre robot
		 			indiceRobot++;
		 		}
		}
		
	}
}

