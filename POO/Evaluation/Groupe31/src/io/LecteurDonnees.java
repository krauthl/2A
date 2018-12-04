package io;


import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

import Enum.NatureTerrain;
import travail.*;


/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que LIRE les infos et les afficher.
 * A vous de modifier ou d'ajouter des méthodes, inspirées de celles présentes
 * (ou non), qui CREENT les objets au moment adéquat pour construire une
 * instance de la classe DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues:
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 * créent les objets adéquats et les ajoutent ds l'instance de
 * DonneesSimulation.
 */
public class LecteurDonnees {


    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     * @throws Exception 
     */
    public static DonneesSimulation lire(String fichierDonnees)
    	
        throws Exception {
    	DonneesSimulation donnees = new DonneesSimulation();
        System.out.println("\n == Lecture du fichier" + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        donnees.setCarte(lecteur.lireCarte());
        donnees.setIncendie(lecteur.lireIncendies());
        donnees.setNbIncendies(donnees.getIncendie().length);
        donnees.setRobot(lecteur.lireRobots());
        donnees.setNbRobots(donnees.getRobot().length);
        donnees.setNbLitresEteindre();
        donnees.setPosInitRobot();
        donnees.getCarte().remplirCasesEau();
        scanner.close();
        System.out.println("\n == Lecture terminee");
       
		return donnees;
    }




    // Tout le reste de la classe est prive!

    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et affiche les donnees de la carte.
     * @throws Exception 
     * @throws ExceptionFormatDonnees
     */
    private Carte lireCarte() throws Exception {
        ignorerCommentaires();
        try {
        	
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	
            Carte carte = new Carte(nbLignes, nbColonnes, tailleCases);
            
            System.out.println("Carte " + nbLignes + "x" + nbColonnes
                    + "; taille des cases = " + tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                	Case maCase = lireCase(lig, col);
                	
                    carte.setCase(lig,col,maCase);
                    if(maCase.getNature() == NatureTerrain.EAU) {
                    	carte.inserCaseEau(maCase);
                    }
                }
            }
            return carte;
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
    }




    /**
     * Lit et affiche les donnees d'une case.
     */
    private Case lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        Case maCase = new Case(lig, col);
       
        String chaineNature = new String();
        //		NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            //			NatureTerrain nature = NatureTerrain.valueOf(chaineNature);

            verifieLigneTerminee();

           
            maCase.setNature(chaineNature);
            return maCase;
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

    }


    /**
     * Lit et affiche les donnees des incendies.
     */
    private Incendie[] lireIncendies() throws DataFormatException {
        ignorerCommentaires();
        try {
        	
            int nbIncendies = scanner.nextInt();
            Incendie [] incendies = new Incendie[nbIncendies];
            
            System.out.println("Nb d'incendies = " + nbIncendies);
            for (int i = 0; i < nbIncendies; i++) {
                incendies[i] = lireIncendie(i);
            }
            return incendies;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et affiche les donnees du i-eme incendie.
     * @param i
     */
    private Incendie lireIncendie(int i) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Incendie " + i + ": ");

        try {
        	Incendie incendie = new Incendie();
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            Case caseIncendie = new Case(lig,col);
            incendie.setPosition(caseIncendie);
            int intensite = scanner.nextInt();
            incendie.setLitresEteindre(intensite);
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            System.out.println("position = (" + lig + "," + col
                    + ");\t intensite = " + intensite);
            return incendie;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et affiche les donnees des robots.
     */
    private Robot[] lireRobots() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            System.out.println("Nb de robots = " + nbRobots); 
            Robot[] robots = new Robot[nbRobots];
            for (int i = 0; i < nbRobots; i++) {
                robots[i] = lireRobot(i);
            }
            return robots;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }


    /**
     * Lit et affiche les donnees du i-eme robot.
     * @param i
     */
    private Robot lireRobot(int i) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Robot " + i + ": ");

        try {
        	
        	Robot robot = null;
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            System.out.print("position = (" + lig + "," + col + ");");
            String type = scanner.next();
            
            switch(type) {
            case "DRONE":
        		robot = new RobotDrone(new Case(lig, col));
        		break; 
        	case "ROUES":
        		robot = new RobotARoues(new Case(lig, col));
        		break;
        	case "CHENILLES":
        		robot = new RobotAChenilles(new Case(lig, col));
        		break;
        	case "PATTES" :
        		robot = new RobotAPattes(new Case(lig, col));
        		break;
        	default:
        		
        		break;
            }
            System.out.print("\t type = " + type);


            // lecture eventuelle d'une vitesse du robot (entier)
            System.out.print("; \t vitesse = ");
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");

            if (s == null) {
                System.out.print("valeur par defaut");
            } else {
                int vitesse = Integer.parseInt(s);
                robot.setVitesse(vitesse);
                System.out.print(vitesse);
            }
            verifieLigneTerminee();

            System.out.println();
            return robot; 
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }




    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
