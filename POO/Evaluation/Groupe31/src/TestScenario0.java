import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import io.LecteurDonnees;
import travail.CalculeChemin;
import travail.Case;
import travail.DonneesSimulation;
import travail.Simulateur;

/**
 * classe de test pour le Scenario 0 du sujet:
 * Deplacer le premier robot vers le nord, quatre fois de suite
 * Erreur: le robot est sorti de la carte
 */

public class TestScenario0 {
	public static void main(String[] args) throws Exception {
		
		if (args.length < 2) {
			System.out.println("Syntaxe: java TestScenario <nomDeFichier> "
					+ "<scenario>");
			System.exit(1);
		}
		try {
			// creation de la fenêtre graphique
			GUISimulator gui = new GUISimulator(1000, 800, Color.WHITE);
			// creation de la carte et ses objets
			DonneesSimulation donnees = LecteurDonnees.lire(args[0]);
			// creation du simulateur
			Simulateur sim = new Simulateur(gui, donnees);

			System.out.println(sim.getDonnees().getRobot()[0]);
			CalculeChemin test = new CalculeChemin(sim.getDonnees().getRobot()[0]);
			test.CalculeRoute(sim.getDonnees().getRobot()[0].getPosition(), new Case(3,3), donnees.getCarte());

			
			System.out.println(test.getDeplacementSequence());
			
		} catch(FileNotFoundException e) {
			System.out.println("fichier" + args[0] + "inconnu ou illisible");
		} catch (DataFormatException e) {
	        System.out.println("\n\t**format du fichier " + args[0] + 
	        		" invalide: " + e.getMessage());
		      
	    }
	}
}
