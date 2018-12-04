package travail;


import Enum.NatureTerrain;
import Enum.TypeRobot;

/**
 * Classe herité de la classe abstraite Robot
 */
public class RobotAChenilles extends Robot{

	public static final double vitesseChenillesParDefaut = 60;
	private static final int reservoirChenilles = 2000;
	private static final long tempsTotalRemplissage = 300000;
	private static final long interventionUnitaire = 80;


// elle peut atteindre 80

	public RobotAChenilles(Case position) {
		this.setPosition(position);
		this.setVitesse(vitesseChenillesParDefaut); //dans la forêt elle diminue de 50%
		this.setType(TypeRobot.Chenilles);
		this.setOccupe(false);
		this.setDateFinTache(0);
		this.setDateCourante(0);
		this.remplirReservoir(reservoirChenilles);
	}

	@Override
	public int capaciteReservoir() {
		return reservoirChenilles;
	}
	@Override
	public void setVitesse(double vitesse){
		if (vitesse <= 80) {
			super.setVitesse(vitesse);
		} else {
			super.setVitesse(80);
		}
	}

	//@Overload
	/**
	 * on appelle cette fonction lorsqu'on change de type de terrain
	 * et la vitesse du robot à chenilles doit changer
	 * en foret cette vitesse diminue de 50%
	 * sinon cela revient à la vitesse normale dans les autres terrains
	 */
	public void setVitesse() {
		//dans la forêt elle diminue de 50%
		if (this.getPosition().getNature() == NatureTerrain.FORET){
			this.setVitesse((this.getVitesse())/2);
		} else {
		// dans les autres terrains on revient à la vitesse de depart
			this.setVitesse(this.getVitesse()*2);
		}
	}

	@Override
	public Boolean deplacementAutoriser(Carte carte, Case caseDestination) {
		if(caseDestination.getNature() == NatureTerrain.EAU || caseDestination.getNature()== NatureTerrain.ROCHE) {
			return false;
		}
		return true;
	}

	@Override
	/**
	 * reservoir de 2000 litres
	 * remplissage complet en 5 min
	 */
	public long tempsRemplissage() {
		int aRemplir = reservoirChenilles - this.getReservoir();
		long temps = (aRemplir*tempsTotalRemplissage)/(long)reservoirChenilles;
		return temps;
		
	}

	@Override
	/**
	 * Intervention unitaire : 100 litres en 8 sec.
	 */
	public long tempsVerserEau(int litresEau) {
		return litresEau * interventionUnitaire;
	}
}
