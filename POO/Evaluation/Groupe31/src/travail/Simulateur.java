package travail;

import java.io.File;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import gui.*;
/**
 * 
 * Classe Simulateur contenant la fenêtre graphique, la carte
 * et ses objets, ainsi qu'une liste ordonnée d'evenements
 * et une date du simulateur
 *
 */

public class Simulateur implements Simulable {

	private GUISimulator gui;
	//private DonneesSimulation donneesInit; // les donnees initiales (ce qu'on lit dans le fichier .map)
	private DonneesSimulation donnees;
	private SortedSet<Evenement> evenements;
	private Iterator<Evenement> iterateur; // un iterateur sur les evenements
	private long date;	
	/**
	 * attribut pour sauvegarder un evenement
	 * voir la methode next() ci-dessous
	 */
	private Evenement evenementPred = null;

	public long getDate() {
		return this.date;
	}
	public SortedSet<Evenement> getEvenements() {
		return this.evenements;
	}


	public void setEvenements(SortedSet<Evenement> evenements) {
		this.evenements = evenements;
	}
	
	public Simulateur(GUISimulator gui, DonneesSimulation donnees) {

		this.gui = gui ;
		gui.setSimulable(this);
		this.donnees = donnees;
		this.evenements = new TreeSet<Evenement> (new ComparateurDate());
		this.date = 1;
		draw();
	}


	 private void draw() {

	 int taille_case = (Integer.min(gui.getHeight(), gui.getWidth())-150)/Integer.min(this.donnees.getCarte().getNbLignes(),this.donnees.getCarte().getNbColonnes());
      gui.reset();	// clear the window
      
      //dessin des cases
     
      for (int i = 0; i < this.donnees.getCarte().getNbLignes(); i++) {
      	for (int j = 0; j <this.donnees.getCarte().getNbColonnes(); j++) {
      		Case c  = this.donnees.getCarte().getCase(i, j);
      		switch(c.getNature()) {
      			case TERRAIN_LIBRE:
      				 gui.ImageElement libre = new gui.ImageElement(j*taille_case+50, i*taille_case+50,  new File("src/travail/img/imageLibre.jpg").getAbsolutePath(), taille_case, taille_case, null);
	   				 gui.addGraphicalElement(libre);
	   				 break;
      				 
      			case HABITAT:
      				 gui.ImageElement habitat = new gui.ImageElement(j*taille_case+50, i*taille_case+50,  new File("src/travail/img/imageHabitat.jpg").getAbsolutePath(), taille_case, taille_case, null);
    				 gui.addGraphicalElement(habitat);
      				 break;

      			case EAU:
      				 gui.ImageElement eau = new gui.ImageElement(j*taille_case+50, i*taille_case+50, new File("src/travail/img/imageEau.jpg").getAbsolutePath(), taille_case, taille_case, null);
     				 gui.addGraphicalElement(eau);
     				 break;

      			case ROCHE:
      				 gui.ImageElement roche = new gui.ImageElement(j*taille_case+50, i*taille_case+50, new File("src/travail/img/imageRoche.jpg").getAbsolutePath(), taille_case, taille_case, null);
     				 gui.addGraphicalElement(roche);
     				 break;
      				 
      			case FORET:
      				 gui.ImageElement foret = new gui.ImageElement(j*taille_case+50, i*taille_case+50, new File("src/travail/img/imageForet.jpg").getAbsolutePath(), taille_case, taille_case, null);
      				 gui.addGraphicalElement(foret);
      				 break;
      		}
		}
	}
      //insertion image de feu
      for(Incendie incendie: this.donnees.getIncendie()) {
    	  if(incendie.getLitresEteindre() > 0) {
	        Case caseIncendie = incendie.getPosition();
	        int caseX = caseIncendie.getLigne();
	        int caseY = caseIncendie.getColonne();
	        int taille = taille_case*3/5;
	        gui.ImageElement feu = new gui.ImageElement(caseY*taille_case + 50 , caseX*taille_case + 50, new File("src/travail/img/imageFeu.png").getAbsolutePath(), taille, taille, null);
	        gui.addGraphicalElement(feu);
    	  }
          
      }
    //insertion image de robot
    for (Robot robot: this.donnees.getRobot()) {
          Case caseRobot = robot.getPosition();
          int caseX = caseRobot.getLigne();
          int caseY = caseRobot.getColonne();
          int taille = taille_case*3/4;
          java.lang.String fileName = null;
          
      switch (robot.getType()) {
            case Chenilles: fileName = new File("src/travail/img/imageRobotChenille.png").getAbsolutePath(); 
            				break;
            case Roues: fileName =  new File("src/travail/img/imageRobotRoues.png").getAbsolutePath();  break;
            case Pattes: fileName =  new File("src/travail/img/imageRobotPattes.png").getAbsolutePath();  break;
            case Drone: fileName = new File("src/travail/img/imageRobotDrone.png").getAbsolutePath(); 
            break;

            default: break;
      }
       ImageElement robotImage = new ImageElement(caseY*taille_case + 50,
                  caseX*taille_case + 50,
                  fileName,
                  taille,
                  taille,
                  null);
       gui.addGraphicalElement(robotImage);         
        
      
 
    }
  }
	 public DonneesSimulation getDonnees() {
		return donnees;
	}


	private void incrementeDate() {
		 this.date +=100;
	 }

	@Override
	/**
	 * lorsque next() est invoquée une méthode incrementeDate() increment
	 * alors la date courante, et le simulateur exécute dans l'ordre tous 
	 * les evenements non encore exécutées jusqu'à cette date
	 */
	public void next() {
		///dans le cas ou on veut executer sans appuyer sur restart
		if(this.iterateur == null ){
			this.restart();
		}
		
		long datePred = -1; // on utilise cette variable pour s'assurer que deux evenements n'arrivent pas à la même date
		Robot robotPred = null; // on s'assure qu'il s'agit de deux evenements sur le même robot à la même date
		this.incrementeDate();
		Evenement evenementCourant = null;
		// j'execute le dernier evenement de l'itération précédente
		// car il n'a pas encore été exécuté
		if(this.evenementPred != null) {
			this.evenementPred.execute(donnees);
			this.draw();
			datePred = this.evenementPred.getDate();
			robotPred = this.evenementPred.getRobot();
		}
		while(this.iterateur.hasNext() ) {
		
			evenementCourant = this.iterateur.next();
			
			if(datePred != -1 && evenementCourant.getDate() == datePred && evenementCourant.getRobot() == robotPred)
				throw new java.lang.RuntimeException("Deux evenements arrivent en même temps sur le même robot");
			miseAjourOcuppationRobot();
			evenementCourant.execute(this.donnees);
			if(evenementCourant.getDate() >= this.date) {	
				break;
			}

			if(evenementCourant.getRobot().estOccupe()) {
				throw new java.lang.RuntimeException("On veut executer un evenement alors que le robot est occupé!");
			}
			evenementCourant.execute(donnees);
			datePred = evenementCourant.getDate();
			robotPred = evenementCourant.getRobot();
			this.draw();	
			
		}
		// on sauvegarde le dernier evenement qu'on a recupéré de l'itérateur
		// car on n'a pas pu l'exécuter (on va l'exécuter au prochain next)
		this.evenementPred = evenementCourant;
		if(!this.iterateur.hasNext()) {
			System.out.println("Simulateur:  Fin d'execution de tous les Evenements "+date);
		}
	}

	
	/** Methode Pour changer l'etat du robot (Occuper ou pas en fonction de la date du simulateur
	 * Si la date du simulateur est > a la date de fin de tache du robot c'est qui a fini sa tache
	 * Sinon laisser l'etat telle qu'il etais 
	 * */
	private void miseAjourOcuppationRobot() {
		for(Robot r : donnees.getRobot()) {
			if(r.estOccupe()) {
				if(r.getDateFinTache()<this.date) {
					r.setOccupe(false);
				}
			}
		}
		
	}


	/**
	 * methode qui permet a l'utilisateur de reinitialiser 
	 * la carte dans son etat initial (celui de la carte du fichier .map)
	 * FIXME ne marche pas tout a fait, reste a faire la copie profonde de donnees
	 */
	@Override
	public void restart() {
		this.iterateur = this.evenements.iterator();
		this.evenementPred = null;
		this.date = 1;
		this.donnees.resetIncendies();
		this.donnees.resetRobots();
		this.draw();
	}
}
