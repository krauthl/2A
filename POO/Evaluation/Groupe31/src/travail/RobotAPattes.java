package travail;

import Enum.NatureTerrain;
import Enum.TypeRobot;

/**
 * Classe herité de la classe abstraite Robot
 */
public class RobotAPattes extends Robot{

	public static final double vitessePattesParDefaut = 30;
	private static final int reservoirPattes = Integer.MAX_VALUE;
	private static final long interventionUnitaire = 100;

	public RobotAPattes(Case position) {
		this.setPosition(position);
		this.setVitesse(vitessePattesParDefaut);// réduite à 10 km/h sur du rocher
		this.setType(TypeRobot.Pattes);
		this.setOccupe(false);
		this.setDateFinTache(0);
		this.setDateCourante(0);
		this.remplirReservoir(reservoirPattes);
	}

	@Override
	public int capaciteReservoir() {
		return reservoirPattes;
	}
	//@Overload
	/**
	 * on appelle cette fonction lorsqu'on change de type de terrain
	 * la vitesse du robot à pattes diminue à 10 km/h sur du rocher
	 * sinon on revient à la vitesse initiale
	 */
	public void setVitesse() {
		if (this.getPosition().getNature() == NatureTerrain.ROCHE){
			this.setVitesse(10);
		} else {
			this.setVitesse(30);
		}
	}

	@Override
	public Boolean deplacementAutoriser(Carte carte, Case caseDestination) {
		if(caseDestination.getNature() == NatureTerrain.EAU) {
			return false;
		}
		return true;
	}
	
	@Override
	public long tempsRemplissage() {
		
		return 0;
		
	}

	@Override
	/**
	 * Intervention unitaire: 10 litres en 1 sec
	 */
	public long tempsVerserEau(int litresEau) {
		return litresEau * interventionUnitaire;
	}
}
