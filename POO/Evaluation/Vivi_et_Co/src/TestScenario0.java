
import io.*;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class TestScenario0 {
    public static void main(String[] args) {
        try {
          // Mise en place de la simulation.
          Simulateur simu = new Simulateur(LecteurDonnees.lire(args[0]));

          // Mise en place de la direction ( on ne sait pas faire mieux )
          Direction dir = Direction.NORD;

          // Mise en place du robot
          Robot drone = simu.donnees.getRobots()[0];

          // Mise en place des evenements;
          for (int i=0; i<4; i++){
              Evenementdeplacement premierDeplacer = new Evenementdeplacement(simu, drone, dir);
            }
        }
        // Partie levee d'exception
        catch (FileNotFoundException e) {
          System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
          System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}
