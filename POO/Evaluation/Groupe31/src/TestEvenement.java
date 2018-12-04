import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import gui.GUISimulator;
import io.LecteurDonnees;
import io.LectureSimulation;
import travail.DonneesSimulation;
import travail.EvenementDeplacer;
import travail.Simulateur;

/**
 * Test des evenements de la carte
 */
public class TestEvenement {
	public static void main(String[] args) throws Exception {
	// crée la fenêtre graphique dans laquelle dessiner
	//   GUISimulator gui = new GUISimulator(800, 600, Color.WHITE);
	// crée l'invader, en l'associant à la fenêtre graphique précédente
	//  DonneesSimulation donnees = LecteurDonnees.lire(args[0]);
	//Simulateur s = new Simulateur(gui, donnees);
	    if (args.length < 2) {
	        System.out.println("Syntaxe: java TestEvenement <nomDeFichier> <senario >");
	        System.exit(1);
	    }
	
	    try {
	        // crée la fenêtre graphique dans laquelle dessiner
	        GUISimulator gui = new GUISimulator(1000, 800, Color.WHITE);
	        // crée la carte et ses objets, en l'associant à la fenêtre graphique précédente
	        DonneesSimulation donnees = LecteurDonnees.lire(args[0]);
	        Simulateur s = new Simulateur(gui, donnees);
	        
	       s.setEvenements(LectureSimulation.lire(args[1],donnees));
	       s.getEvenements().first();
	    } catch (FileNotFoundException e) {
	        System.out.println("fichier " + args[0] + "  inconnu ou illisible");
	    } catch (DataFormatException e) {
	        System.out.println("\n\t**format du fichier " + args[0] + 
	        		" invalide: " + e.getMessage());
		      
	    }
	}
}
	