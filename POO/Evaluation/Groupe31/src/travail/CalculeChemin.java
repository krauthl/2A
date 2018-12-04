package travail;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.Stack;
import Enum.Direction;

/**
 * Classe qui est utilisé pour calculer le plus court chemin entre la position
 * d'un robot et une case destination. 
 * L'algorithme de Dijkstra est utilisé pour calculer le plus court chemin entre 
 * deux cases. 
 * La classe contient egalement des méthodes qui calculent le temps de deplacement
 * d'un robot vers une case destination. 
 */
public class CalculeChemin {
	
	private Robot robot;

	private Stack<Case> deplacementSequence;

	private int tempsNecessaire;
	
	public CalculeChemin(Robot robot ) {
		this.robot = robot ;
		
	}

	public Robot getRobot() {
		return robot;
	}
	// fonction inutile (on a deja le constructeur) à enlever a mon avis
	public void setRobot(Robot robot) {
		this.robot = robot;
	}


	public Stack<Case> getDeplacementSequence() {

		return deplacementSequence;
	}


	public int getTempsNecessaire() {
		return tempsNecessaire;
	}
	
	/**
	 * application de l'algorithme de Dijkstra pour calculer le plus court chemin
	 * entre la position du robot et la caseDestination
	 * @param caseDestination
	 * @param carte
	 */
	public boolean CalculeRoute(Case caseActuelle, Case caseDestination, Carte carte) {
		this.deplacementSequence = new Stack <Case>();
		int nbCase = carte.getNbLignes()*carte.getNbColonnes(); // on calcule le nb total de cases ds la carte
		
		Distance distance [] = new Distance[nbCase] ;
		
		if(caseActuelle.equals(caseDestination)) {
			return true;
		}
		
		
		Distance distanceActuelle  = initDistace(distance, carte, caseActuelle);
		
		
		while(! distanceActuelle.getCaseActuelle().equals(caseDestination) && distanceActuelle.getDistanceSource()<Integer.MAX_VALUE) {
			
			//choisir un voisin de la case actuelle
			distanceActuelle = choisirSuivant(distanceActuelle, carte, distance);
		
		}
		// on verifie si on a une route 
		if(distanceActuelle.getDistanceSource() < Integer.MAX_VALUE) {
			this.construireRoute(distanceActuelle, caseActuelle);
		}
			return !this.deplacementSequence.isEmpty();	

	}

	/**
	 * reconstruit le plus court chemin calcule, en partant de la case destination
	 * vers la case de depart du robot
	 * @param distance
	 * @param carte
	 * @param caseActuelle
	 */
	private void construireRoute( Distance distanceActuelle, Case caseActuelle) {

	
		
		while(! distanceActuelle.getCaseActuelle().equals(caseActuelle)) {
			
			this.deplacementSequence.push(distanceActuelle.getCaseActuelle());
			distanceActuelle = distanceActuelle.getDistancePere();
			
		}
		
	}


	/**
	 * Calcule la case qui suit caseActuelle 
	 * @param caseActuelle
	 * @param carte
	 * @param dejaVisite
	 * @param distance
	 * @return la case suivante a choisir pour le plus court chemin
	 */
	private Distance choisirSuivant(Distance distanceActuelle, Carte carte, Distance distance[]) {	
		
		Distance prochaineCase = null;
		Case voisin = null ;
		distanceActuelle.setDejaVisiter(true);
		for(Direction dir : Direction.values()) {
			
			voisin = carte.getVoisin(distanceActuelle.getCaseActuelle(), dir);
		
				if(voisin != null) {
						
						if(robot.deplacementAutoriser(carte, voisin)) {
							
								Distance distanceVoisin = distance[voisin.getLigne()*carte.getNbColonnes() + voisin.getColonne()];
								
								//mettre a jour la distance entre la case Actuelle est ses voisins si il son pas deja visite 
								if(distanceVoisin != null) {
									
									if( distanceVoisin.getDistanceSource() > (distanceActuelle.getDistanceSource() + robot.tempsDeplacementVoisin(carte.getTailleCases()))  ) {
										
										distanceVoisin.setDistanceSource(distanceActuelle.getDistanceSource() + robot.tempsDeplacementVoisin(carte.getTailleCases() ));
										distanceVoisin.setDistancePere(distanceActuelle);								
									}
								
								}
	
						}
				}		
				
		}	
		
		
		prochaineCase = RechercheDistanceMin(distance);
		return  prochaineCase;
		
			
	}


	/**
	 * Fonction qui revoie la plus petit Distance qui a la plus petit distance par rapport a la source 
	 * @paramtableau de Distance
	 * @return Distance
	 */

	private Distance RechercheDistanceMin(Distance[] distance) {
		double min = Double.MAX_VALUE	;
		int indiceMin =-1 ;
		for(int i=0;i<distance.length;i++ ) {
			if(! distance[i].isDejaVisiter()) {
				if(distance[i].getDistanceSource() <= min) {
					min = distance[i].getDistanceSource();
					indiceMin = i;
				}
			}
		}
		
		if(indiceMin >-1) {
			return distance[indiceMin];
		}
		return null;
	}

	
	/**
	 * Initialise le Tableau Distance par des infini pour toutes les case de la carte sauf la case source qui est a une distance 0 
	 * @param distance Tableau qui contient tout les Distance de la carte 
	 * @param carte
	 * @param caseActuelle : la Case source 
	 * @return Une reference vers la Distance de la case source
	 */
	private Distance initDistace(Distance[] distance, Carte carte, Case caseActuelle) {
		
		Distance d = null;
		for(int i=0; i<distance.length; i++ ) {
			if(carte.getCase(i/carte.getNbColonnes(),i%carte.getNbColonnes()).equals(caseActuelle)){
				 d = new Distance(carte.getCase(i/carte.getNbColonnes(),i%carte.getNbColonnes()),0);
				distance[i] = d;
			}else {
				distance[i] = new Distance(carte.getCase(i/carte.getNbColonnes(),i%carte.getNbColonnes()));
			}
				
		}
		return d;
	}	
	

	/**
	 * calcul des evenements après avoir calculé le plus petit chemin
	 * on programme la serie d'evenements de deplacement elementaires
	 * correspondant au chemin optimal
	 * @param date : la date a la quelle le premier evenement sera programmer
	 * @param evenement : la liste des evenement pour ajouter les deplacement du robot en question au evenement du simulateur
	 * @param int tailleCase
	 * @return la date a la quelle le robot finira la Tache
	 */
	public long calculDeplacements(long date, SortedSet<Evenement> evenements, int tailleCase) {
		Case caseCourante;
		long dateCourante = date;
		double tempsDeplacement;
		Evenement evenement;
		while(! this.deplacementSequence.isEmpty()) {

			caseCourante = this.deplacementSequence.pop(); // enleve la tete de la queue
			tempsDeplacement = robot.tempsDeplacementVoisin(tailleCase);
			dateCourante += tempsDeplacement;
			// FIXME quel est l'utilité de tempsDeplacement en tant qu'attribut de la classe Robot?
			evenement = new EvenementDeplacer(dateCourante, robot, caseCourante, tempsDeplacement);
			evenements.add(evenement);
		}
		tempsDeplacement = robot.tempsDeplacementVoisin(tailleCase);
		dateCourante += tempsDeplacement;
		robot.setDateFinTache(dateCourante);
		return dateCourante;

	}
	
	/**
	 * Calcule le deplacement sans cree les evenement, elle est necessaire pour la strategie 2 ou on cherche le robot le plus rapide
	 * La fonction calcule en fonction de la taille case le temps necessaire pour le robot en question pour ce deplacer de sa position a la dernier case de DeplacementSequence 
	 * @param date : la date de debut de l'evenement
	 * @param tailleCase : servant au calcule de temps de deplacement 
	 * @return temps de deplacement 
	 * */
	public long calculDeplacements(long date, int tailleCase) {
		long dateCourante = date;
		double tempsDeplacement;
		int nbCaseVisite = 0 ;
		while(nbCaseVisite < this.deplacementSequence.size()) {
			nbCaseVisite ++;
			tempsDeplacement = robot.tempsDeplacementVoisin(tailleCase);
			dateCourante += tempsDeplacement;
			// FIXME quel est l'utilité de tempsDeplacement en tant qu'attribut de la classe Robot?
		}
		return dateCourante;

	}


}
