package travail;

import Enum.TypeRobot;
import Enum.NatureTerrain;

/**
 * Classe herité de la classe abstraite Robot
 */
public class RobotARoues extends Robot {



	public static final double vitesseRouesParDefaut = 80;
	private static final int reservoirRoues = 5000;
	private static final long tempsTotalRemplissage = 600000;
	
	public RobotARoues(Case position) {
		this.setPosition(position);
		this.setVitesse(vitesseRouesParDefaut);
		this.setType(TypeRobot.Roues);
		this.setOccupe(false);
		this.setDateFinTache(0);
		this.setDateCourante(0);
		this.remplirReservoir(reservoirRoues);

	}

	@Override
	public int capaciteReservoir() {
		return reservoirRoues;
	}
	
	@Override
	public void setVitesse() {
		// TODO Auto-generated method stub
		// fait rien
	}
	
	public Boolean deplacementAutoriser(Carte carte, Case caseDestination) {
		if(caseDestination.getNature() == NatureTerrain.EAU || caseDestination.getNature() == NatureTerrain.ROCHE
				|| caseDestination.getNature() == NatureTerrain.FORET) {
			return false;
		}
		return true;

	}

	@Override
	public long tempsRemplissage() {
		int aRemplir = reservoirRoues - this.getReservoir();
		long temps = (aRemplir*tempsTotalRemplissage)/(long)reservoirRoues;
		return temps;
		
	}

	@Override
	/**
	 * Intervention unitaire: 100 litres en 5 sec
	 */
	public long tempsVerserEau(int litresEau) {
		return (litresEau * 5 * 1000) / 100;
	}


}
