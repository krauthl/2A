package travail;

import Enum.TypeRobot;

/**
 * Classe herité de la classe abstraite Robot
 */
public class RobotDrone extends Robot {




	public static final double vitesseDroneParDefaut = 100;
	private static final int reservoirDrone = 10000;
	private static final long tempsTotalRemplissage = 1800000;
	// ne dépasse pas 150

	public RobotDrone(Case position) {
		this.setPosition(position);
		this.setVitesse(vitesseDroneParDefaut);
		this.setType(TypeRobot.Drone);
		this.setOccupe(false);
		this.setDateFinTache(0);
		this.setDateCourante(0);
		this.remplirReservoir(reservoirDrone);
	}

	@Override
	public int capaciteReservoir() {
		return reservoirDrone;
	}
	
	@Override
	public void setVitesse(double vitesse) {
		if (vitesse<=150) {
			super.setVitesse(vitesse);
		} else {
			super.setVitesse(150);
		}
	}

	@Override
	public void setVitesse() {
		// TODO Auto-generated method stub
		// fait rien
	}
	
	public Boolean deplacementAutoriser(Carte carte, Case caseDestination) {
		return true;

	}

	@Override
	public long tempsRemplissage() {
		int aRemplir = reservoirDrone - this.getReservoir();
		long temps = (aRemplir*tempsTotalRemplissage)/reservoirDrone;
		return temps;
	}

	@Override
	/**
	 * vide la totalité du réservoir en 30 secondes
	 */
	public long tempsVerserEau(int litresEau) {
		return (litresEau * 30 * 1000) /reservoirDrone;
	}




}
