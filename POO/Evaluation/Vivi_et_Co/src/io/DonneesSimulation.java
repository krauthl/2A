package io;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

/**
 * Classe qui contient les donnees contenues dans le .map
 */
public class DonneesSimulation{

    private Carte carte;
    private Incendie[] incendies;
    private Robot[] robots;
    public String fichier;

    /**
    * Constucteur de DonneesSimulation
    */
    public DonneesSimulation(Carte c, Incendie i[], Robot r[], String file)
    throws FileNotFoundException, DataFormatException
    {
         this.setCarte(c);
         this.setIncendies(i);
         this.setRobot(r);
         this.fichier = file;
     }

/**
 * Setter de case
 */
     public void setCarte(Carte c){
         this.carte =c ;
     }

/**
 * Setter d'incendie
 */
     public void setIncendies(Incendie i[]){
         this.incendies = i;
     }

/**
 * Setter de robot
 */
     public void setRobot(Robot r[]){
         this.robots = r;
     }

/**
 * Retourne la carte
 */
     public Carte getCarte(){
         return this.carte;
     }

/**
 * retourne la liste des incendies
 */
     public Incendie[] getIncendies(){
         return this.incendies;
     }

/**
 * Retourne la liste des robots
 */
     public Robot[] getRobots(){
         return this.robots;
     }

     /**
     * Affiche textuellement les incendies
     */
    private static String AfficherIncendies(Incendie[] incendies){
         String returned_string = "\n\n\t#Incendies";
         for (int i=0; i<incendies.length; i++){
             returned_string += "\nIncendie " + i + ": Position : (" + incendies[i].getLigne()
                    + "," + incendies[i].getColonne() + ")\tIntensite : "
                    + incendies[i].getIntensite();
         }
         return returned_string;
    }

    /**
    * Affiche textuellement les robots
    */
    private static String AfficherRobots(Robot[] robots){
         String stringReturned = "\n\n\t#Robots";
         for (int i=0; i<robots.length; i++){
             stringReturned += "\nRobot " + i + ": Position : (" + robots[i].getLigne()
                    + "," + robots[i].getColonne() + ")\t type : " + "\t Vitesse : " + robots[i].getVitesse();
         }
         return stringReturned;
    }

    /**
    * Affiche textuellement les DonneesSimulation
    */
    public String afficher(){
        String stringReturned = "\t #Carte" + this.carte.ToString();
        stringReturned += AfficherIncendies(this.incendies) + AfficherRobots(this.robots);
        return stringReturned;
    }

    /**
    * Mets les parametres Robots et Incendie de this aux etats de nouvelle
    * SANS MODIFIER l'adresse des robots ou des incendies
    */
    public void RemettreInitial(DonneesSimulation nouvelle){
         for (int len=0; len < this.getRobots().length; len++){
             this.getRobots()[len].setLigne(nouvelle.getRobots()[len].getLigne());
             this.getRobots()[len].setColonne(nouvelle.getRobots()[len].getColonne());
             switch(this.getRobots()[len].getTypeRobot()){
                 case DRONE:
                    this.getRobots()[len].setReservoir(10000);
                    break;
                 case ROUES:
                    this.getRobots()[len].setReservoir(5000);
                    break;
                 case CHENILLES:
                    this.getRobots()[len].setReservoir(2000);
                    break;
                default:
                    break;
             }
            //  this.getRobots()[len].setEtat(nouvelle.getRobots()[len].getEtat());
         }
         for (int len=0; len < this.getIncendies().length; len++){
             this.getIncendies()[len].setIntensite(nouvelle.getIncendies()[len].getIntensite());
         }
     }
}
