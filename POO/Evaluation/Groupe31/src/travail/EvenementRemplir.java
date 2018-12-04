package travail;

import travail.Robot;
import Enum.NatureTerrain;
import Enum.Direction;

/**
 * Classe heritee de la classe Evenement
 * servant à remplir le reservoir d'un robot
 * lorsqu'il est vide.
 *
 */
public class EvenementRemplir extends Evenement {
	
	private Robot robot;
	
	public EvenementRemplir(long date, Robot robot) {
		super(date);
		this.robot = robot;
		this.robot.setDateFinTache(date + robot.tempsRemplissage());

	}
	
	@Override
	void execute(DonneesSimulation donnees) {
		
		Case position = this.robot.getPosition(); 
		switch (this.robot.getType()) {
			case Drone:
				if (position.getNature() == NatureTerrain.EAU) {
					robot.remplirReservoir(robot.capaciteReservoir());
				}
				break;
			case Chenilles: case Roues:
				Case nord = donnees.getCarte().getVoisin(position, Direction.NORD);
				Case sud = donnees.getCarte().getVoisin(position, Direction.SUD);
				Case est = donnees.getCarte().getVoisin(position, Direction.EST);
				Case ouest = donnees.getCarte().getVoisin(position, Direction.OUEST);
				if ( ((nord != null) && (nord.getNature() == NatureTerrain.EAU))
					|| ((sud!= null) && (sud.getNature() == NatureTerrain.EAU))
					|| ((est != null) && (est.getNature() == NatureTerrain.EAU))
					|| ((ouest != null) && (ouest.getNature() == NatureTerrain.EAU))
					) 
				{
					robot.remplirReservoir(robot.capaciteReservoir());
				}
				
			break;
			default: 
				
				break;
		}
	}

	@Override
	Robot getRobot() {
		return this.robot;
	}
	
	@Override
	public String toString() {
		String string = " evenement remplir reservoir: " + robot.toString() + "a la date "+this.getDate();
		return string ;
	}
}