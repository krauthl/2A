import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import io.LecteurDonnees;
import io.LectureSimulation;
import travail.DonneesSimulation;
import travail.Simulateur;

/**
 * classe de test pour le Scenario 1 du sujet:
 * Deplacer le deuxieme robot vers le nord, en (5, 5) ... etc.
 * A la fin une case feu doit être éteinte
 */

public class TestScenario1  {
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
			sim.setEvenements(LectureSimulation.lire(args[1], donnees));
			
			
		} catch(FileNotFoundException e) {
			System.out.println("fichier" + args[0] + "inconnu ou illisible");
		} catch (DataFormatException e) {
	        System.out.println("\n\t**format du fichier " + args[0] + 
	        		" invalide: " + e.getMessage());
	    }
	}
}