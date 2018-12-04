package travail;

/**
 * Classe heritee de la classe Evenement
 * servant à verser de l'eau sur une
 * case incendie
 */
public class EvenementVerserEau extends  Evenement{
	
	
	private Robot robot;
	
	public EvenementVerserEau (long date, Robot robot, int litresEau ) {
		super(date);
		this.robot = robot;
		this.robot.setDateFinTache(date + robot.tempsVerserEau(Math.min(litresEau, robot.getReservoir())));	
	}
		
	@Override
	void execute(DonneesSimulation donnees) {
		// on vérifie si le robot est sur une case Incendie pour pouvoir intervenir
		Case posRobot = robot.getPosition();
		
		int indiceIncendie = donnees.estCaseIncendie(posRobot);
		if(indiceIncendie >= 0) {

			// on fait le robot intervenir sur la case incendie
			// on verse de l'eau sur l'incendie et on calcule le nb de litres d'eau restantes à verser
			int eauVerserRestant = robot.deverserEau(donnees.getIncendie()[indiceIncendie].getLitresEteindre());
			donnees.getIncendie()[indiceIncendie].setLitresEteindre(eauVerserRestant);

		}
	}

	@Override
	public String toString() {
		String string = " evenement verser eau " + robot.toString() + "a la date "+this.getDate()+"\n";
		return string ;
	}
	@Override
	Robot getRobot() {
		return this.robot;
	}

}
