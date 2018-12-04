import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import io.LecteurDonnees;
import travail.ChefPompierEvoluee;
import travail.DonneesSimulation;
import travail.Simulateur;
import travail.TestStrategie;

public class TestThread {
public static void main(String[] args) throws Exception {
		
		if (args.length < 1) {
			System.out.println("Syntaxe: java TestScenario <nomDeFichier> ");
			System.exit(1);
		}
		try {
			// creation de la fenêtre graphique
			GUISimulator gui = new GUISimulator(1000, 800, Color.WHITE);
			// creation de la carte et ses objets
			DonneesSimulation donnees = LecteurDonnees.lire(args[0]);
			// creation du simulateur
			Simulateur sim = new Simulateur(gui, donnees);
			TestStrategie chef = new TestStrategie(donnees, sim.getEvenements(), sim); // sim.getDate() renvoie 1
			chef.strategieEvoluee();
			
			
		} catch(FileNotFoundException e) {
			System.out.println("fichier" + args[0] + "inconnu ou illisible");
		} catch (DataFormatException e) {
	        System.out.println("\n\t**format du fichier " + args[0] + 
	        		" invalide: " + e.getMessage());
	    }
	}
}
