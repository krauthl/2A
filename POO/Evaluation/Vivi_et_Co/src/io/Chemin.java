package io;

import java.util.*;

/**
*  Cette classe sert a trouver le plus court chemin,s'il existe pour le
* robot entre,  entre la case d'arrivee et de celle de depart.
*/
public class Chemin {
    private Case depart;
    private Case arrivee;
    private Robot robot;
    private Simulateur simu;
    private long temps;
    private LinkedList<Case> liste_cases; //correspond au chemin final
    public boolean possible;
    public boolean continuer_a_iterer;

/**
 * Constructeur de la classe
 */
    public Chemin(Case d, Case a, Robot r, Simulateur s){
        this.setDepart(d);
        this.setArrivee(a);
        this.robot = r;
        this.simu = s;
        this.setTemps(0);
        this.possible = false;
        this.continuer_a_iterer = true;
        this.calculer();
    }

    /**
    * Calcule le temps que met le robot a aller a une autre case depuis
    * la case depart
    */
    private int getDistanceTemp(Case courante, Simulateur simu){
         return (simu.donnees.getCarte().getTailleCases()/ (int) this.getRobot().getVitesse());
    }


    /**
    * Pour toutes les cases deja dans la table, visite les voisins, note
    * le temps et le chemin emprunte pour s'y rendre, et les rentre dans
    * les dictionnaires.
    * On cesse d'iterer lorsque les dictionnaires sont invariants par
    * cette fonction.
    */
    private void iterer(Map<Case, Integer> distance_temporelle, Map<Case, LinkedList<Case>> chemin_jusqua_case){
         this.continuer_a_iterer = false;
         Iterator<Case> cases = distance_temporelle.keySet().iterator();
         int size = distance_temporelle.keySet().size();
         // Mise en place d'une copie car on modifie l'iterateur lors du parcourt
         Case[] copie = new Case[size];
         for (int indice = size; indice>0; indice--){
             copie[size-indice] = cases.next();
         }
         for (int indice = 0; indice<size; indice++){
             Case current = copie[indice];
             for (Direction dir : Direction.values()) {
                 if (this.getSimu().donnees.getCarte().voisinExiste(current, dir)){
                     Case voisine = this.simu.donnees.getCarte().getVoisin(current, dir);
                     if (!this.getRobot().test_deplacement(voisine)){
                         continue;
                     }
                     Integer dist = new Integer(getDistanceTemp(current, this.getSimu()));
                     dist += distance_temporelle.get(current);
                     if (distance_temporelle.containsKey(voisine)){
                         if (dist < distance_temporelle.get(current)){
                             // Le temps est plus petit : un nouveau chemin est trouve, et plus rapide
                             distance_temporelle.put(voisine, dist);
                             LinkedList<Case> chem = chemin_jusqua_case.get(current);
                             LinkedList<Case> nouv = new LinkedList<Case>();
                             int n = chem.size();
                             for (int indice_copie=0; indice_copie < n; indice_copie++){
                                 nouv.add(chem.get(indice_copie));
                             }
                             nouv.add(voisine);
                             chemin_jusqua_case.put(voisine, nouv);
                             this.continuer_a_iterer = true;
                         }
                     }
                     else{
                         // On ajoute dans le dico la case
                        distance_temporelle.put(voisine, dist);
                        LinkedList<Case> chem = chemin_jusqua_case.get(current);
                        LinkedList<Case> nouv = new LinkedList<Case>();
                        int n = chem.size();
                        for (int indice_copie=0; indice_copie < n; indice_copie++){
                         nouv.add(chem.get(indice_copie));
                        }
                        nouv.add(voisine);
                        chemin_jusqua_case.put(voisine, nouv);
                        this.continuer_a_iterer = true;
                     }
                 }
             }
         }
     }

     /**
     * Calcule le plus court chemin entre d et a
     */
    private void calculer(){
         this.continuer_a_iterer = true;
         Case depart = this.getDepart();
         Case arrivee = this.getArrivee();
         // Creation du dictionnaire contenant le temps pour aller dans toutes les autres cases depuis le depart
         Map<Case, Integer> distance_temporelle = new HashMap<Case, Integer> ();
         distance_temporelle.put(depart, 0);
         // Creation du dictionnaire contenant la file des cases a parcourir pour aller a la case voulue depuis le depart
         // en temps optimal
         Map<Case, LinkedList<Case>> chemin_jusqua_case = new HashMap<Case, LinkedList<Case>> ();
         int taille_tableau = this.getSimu().donnees.getCarte().getNbLignes()* this.getSimu().donnees.getCarte().getNbColonnes();
         // Debut de l'iteration
         LinkedList<Case> chemin_initial = new LinkedList<Case>();
         chemin_jusqua_case.put(depart, chemin_initial);
         while (this.continuer_a_iterer){
             this.iterer(distance_temporelle, chemin_jusqua_case);
         }
         this.possible = chemin_jusqua_case.containsKey(arrivee);
         if (this.possible){
             this.setTemps(distance_temporelle.get(arrivee));
             this.setListeCases(chemin_jusqua_case.get(arrivee));
         }
    }

    /**
    * Creation des evenements pour que le robot se deplace
    */
    public void deplacement(){
        Case prece = this.getSimu().getPosition(this.getRobot());//this.getSimu().donnees.getCarte().getTableauDeCases()[this.getRobot().getLigne()* this.getSimu().donnees.getCarte().getNbColonnes()+this.getRobot().getColonne()];
        for (int i = 0; i<this.getSimu().donnees.getCarte().getNbLignes(); i++){
            for (int j=0; j<this.getSimu().donnees.getCarte().getNbColonnes(); j++){
                if (prece.getLigne() == i && prece.getColonne() == j){
                    prece = this.getSimu().donnees.getCarte().getTableauDeCases()[i* this.getSimu().donnees.getCarte().getNbColonnes()+j];
                }
            }
        }
        LinkedList<Case> parcourt = this.getListeCases();
        int n = parcourt.size();
        for (int indice=0; indice < n; indice++){
            Direction dir = trouverDirection(this.getSimu(), prece, parcourt.get(indice));
            prece = parcourt.get(indice);
            Evenementdeplacement deplacement = new Evenementdeplacement(this.getSimu(), this.getRobot(), dir);
        }
    }

    /**
    * Affiche le trajet emprunte pour aller a la case d'arrivee en temps
    * minimal
    */
    public void afficherTrajet(LinkedList<Case> trajet){
        int n = trajet.size();
        for (int indice=0; indice < n; indice++){
            System.out.println(trajet.get(indice));
        }
    }

    /**
    * Permet de trouver où se trouve cardinalement la case endroit par
    * rapport a la case prece
    */
    private Direction trouverDirection(Simulateur simu, Case prece, Case endroit){
        int ligne_robot = prece.getLigne();
        int col_robot = prece.getColonne();
        Direction dir = Direction.SUD;
        int diff_ligne  = ligne_robot - endroit.getLigne();
        int diff_col = col_robot - endroit.getColonne();
        if (diff_ligne == 1){dir = Direction.NORD;}
        if (diff_col == 1){dir = Direction.OUEST;}
        if (diff_col == -1){dir = Direction.EST;}
        return dir;
    }

    // Set et get...
    /**
     * Setter de case de depart
     */
    public void setDepart(Case d){
    this.depart = d;
    }

    /**
     * retourne la case depart
     */
    public Case getDepart(){
    return this.depart;
    }

    /**
     * setter de la case d'arrivee
     */
    public void setArrivee(Case a){
    this.arrivee = a;
    }

    /**
     * retourne la case d'arrivee
     */
    public Case getArrivee(){
    return this.arrivee;
    }

    /**
     * retourne le robot concerne par le chemin
     */
    public Robot getRobot(){
    return this.robot;
    }

    /**
     * retourne la simulation
     */
    public Simulateur getSimu(){
    return this.simu;
    }

    /**
     * retourne la liste des cases empruntes par le chemin
     */
    public LinkedList<Case> getListeCases(){
        return this.liste_cases;
    }

    /**
     * setter de liste_cases
     */
    public void setListeCases(LinkedList<Case> liste){
        this.liste_cases = liste;
    }

    /**
     * retourne le temps mis pour aller jusqu'a l'arrivee par ce chemin
     */
    public long getTemps(){
        return this.temps;
    }

    /**
     * setter de temps
     */
    public void setTemps(long time){
        this.temps = time;
    }
    // Fin set et get...

    public String toString(){
        return "[" + this.liste_cases + "]";
    }
}
