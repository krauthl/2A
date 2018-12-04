package travail;

/**
 * Classe heritee de la classe Evenement
 * utilisé pour le déplacement des robots 
 */
public class EvenementDeplacer extends Evenement {
	
	private Robot robot;
	private Case caseDestination;
	private double tempsDeDeplacement;
	
	public EvenementDeplacer(long date, Robot robot, Case caseDestination, double tempsDeDeplacement) {
		super(date);
		this.robot = robot;
		this.caseDestination = caseDestination;
		this.tempsDeDeplacement = tempsDeDeplacement;
	}
	
	
	public Case getCaseDestination() {
		return this.caseDestination;
	}
	
	public double getTempsDeDeplacement() {
		return this.tempsDeDeplacement;
	}
	
	 

	@Override
	public void execute(DonneesSimulation donnees) {
		
		if(this.robot.deplacementAutoriser(donnees.getCarte(), this.caseDestination)) {
			robot.setPosition(this.caseDestination);
		}
		else {
			 throw new RuntimeException("Deplacement Incorrecte");
		}
		
	}
	
	
	@Override
	public String toString() {
		String string = " evenement deplacer: " + robot.toString() + " se deplace a la case  "+ caseDestination.toString() + "a la date "+this.getDate();
		return string ;
	}


	@Override
	Robot getRobot() {
		return this.robot;
	}
}