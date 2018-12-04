package io;


import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

/**
 * Classe servant à lire les données contenues dans  la carte .map
 */
public class LecteurDonnees {


    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est methode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier a lire
     */
    public static DonneesSimulation lire(String fichierDonnees)
        throws FileNotFoundException, DataFormatException {
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        Carte carte = lecteur.lireCarte();
        Incendie incendies[];
        incendies = lecteur.lireIncendies();
        Robot robots[];
        robots = lecteur.lireRobots();
        scanner.close();
        DonneesSimulation donneesSimulation = new DonneesSimulation(carte, incendies, robots, fichierDonnees);
        if (fichierDonnees.equals("cartes/spiralOfMadness-50x50.map")){
            System.out.println("\fUn peu de patience, c'est un peu long sur cette carte. N'appuyez pas sur lecture");
        }
        return donneesSimulation;
    }




    // Tout le reste de la classe est prive!

    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    public LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et affiche les donnees de la carte.
     * @throws ExceptionFormatDonnees
     */
    public Carte lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();
            Case cases[];
            cases = new Case[nbLignes*nbColonnes];
                    for (int lig = 0; lig < nbLignes; lig++) {
                        for (int col = 0; col < nbColonnes; col++) {
                        cases[lig*nbColonnes + col] = this.lireCase(lig, col);
                        }
            }
            Carte carte = new Carte(tailleCases, nbLignes, nbColonnes, cases);
            return carte;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }


    /**
     * Lit et affiche les donnees d'une case.
     */
    public Case lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        String chaineNature = new String();
        NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            nature = NatureTerrain.valueOf(chaineNature);

            verifieLigneTerminee();
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }
        Case caseActive = new Case(lig, col, nature);
        return caseActive;
    }


    /**
     * Lit et affiche les donnees des incendies.
     */
    public Incendie[] lireIncendies() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            if (nbIncendies <= 0){
                throw new IllegalArgumentException("Il n'y a pas le feu en fait...");
            }
            Incendie incendies[];
            incendies = new Incendie[nbIncendies];
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
    public Incendie lireIncendie(int i) throws DataFormatException {
        ignorerCommentaires();
        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            double intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            Incendie incendie = new Incendie(lig, col, intensite);
            return incendie;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et affiche les donnees des robots.
     */
    public Robot[] lireRobots() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            if (nbRobots <= 0){
                throw new IllegalArgumentException("Il n'y a pas de robots en fait...");
            }
            Robot robots[];
            robots = new Robot[nbRobots];
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
    public Robot lireRobot(int i) throws DataFormatException {
        ignorerCommentaires();

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            String type = scanner.next();
            String s = scanner.findInLine("(\\d+)");
            int vitesse;
            TypeRobot typeRobot = TypeRobot.valueOf(type);
            if (s == null) {
                vitesse = VitesseDefaut(typeRobot);
            } else {
                vitesse = Integer.parseInt(s);
            }
            verifieLigneTerminee();

            switch (typeRobot){
              case DRONE:
                Robot drone = new Robotdrone(lig, col, vitesse);
                return drone;

              case ROUES:
                Robot roues =  new Robotaroues(lig, col, vitesse);
                return roues;

              case PATTES:
                Robot pattes = new Robotapattes(lig, col, vitesse);
                return pattes;

              case CHENILLES:
                  Robot chenilles = new Robotachenilles(lig, col, vitesse);
                  return chenilles;

              default:
                  Robot dronee = new Robotdrone(lig, col, vitesse);
                  return dronee;
            }
        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }

private static int VitesseDefaut(TypeRobot type){
    /**
     * Renvoie la vitesse par defaut du robot si elle n'est pas
     * precisee dans le .map
     */
     int vitesseDefaut;
     switch(type){
        case CHENILLES:
            vitesseDefaut = 60;
            break;
        case ROUES:
            vitesseDefaut = 80;
            break;
        case PATTES:
            vitesseDefaut = 30;
            break;
        case DRONE:
            vitesseDefaut = 100;
            break;
        default:
            vitesseDefaut = 0;
    }
    return vitesseDefaut;
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
