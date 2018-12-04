package io;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.util.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;

/**
 * Classe contenant toutes les donnees pour la simulation.
 * Depuis cette classe, on peut acceder a toutes les autres
 */
public class Simulateur implements Simulable {

    public DonneesSimulation donnees;

    public int pas;
    public int time;

    public LinkedList<Evenement> evenements = new LinkedList<Evenement>();
    public Case[] positions;
    public double[] reservoirs;

    public GUISimulator gui = new GUISimulator(1024, 800, Color.BLACK);

    /**
    *  Les les donnees dans data, et cree la simulation associee.
    */
    public Simulateur(DonneesSimulation data) {
        gui.setSimulable(this);
        this.donnees = data;
        this.time = 0;
        this.pas = 1 + this.donnees.getCarte().getTailleCases()/500;
        this.positions = new Case[this.donnees.getRobots().length];
        this.reservoirs = new double[this.donnees.getRobots().length];
        for (int i =0; i<this.donnees.getRobots().length; i++){
            int lig = this.donnees.getRobots()[i].getLigne();
            int col = this.donnees.getRobots()[i].getColonne();
            this.positions[i] = this.donnees.getCarte().getTableauDeCases()[lig* this.donnees.getCarte().getNbColonnes()+col];
            this.reservoirs[i] = this.donnees.getRobots()[i].getReservoir();

        }
        draw();
        restart();
    }

    /**
    * Trouve dans la liste des evenements ceux dont la date est passee,
    * et les execute
    */
    public void executeEvenements(){
         Iterator<Evenement> iter = evenements.iterator();
        int taille = this.evenements.size();
        Evenement[] copie = new Evenement[taille];
        for (int indice = 0; indice< taille; indice++){
            copie[indice] = iter.next();
        }
           for (int indice = 0; indice < taille; indice++){
             Evenement eve = copie[indice];
             if (this.time <= eve.getDate() &  eve.getDate() < this.time + this.pas){
                eve.execute();
             }
         }
    }

    /**
    * Ajoute un evenement
    */
    public void addEvenement(Evenement e){
         this.evenements.add(e);
    }

    /**
    * Affiche les Evenements dans le tableau d'evenements
    */
    public void AfficherEvenements(){
         Iterator<Evenement> iter = evenements.iterator();
         int i=0;
         while (iter.hasNext()){
            Evenement eve = iter.next();
            System.out.println(i +  " : " + eve);
            i++;
         }
    }

    /**
    * Retourne true s'il n'y a plus d'evenements en cours
    */
    private boolean simulationTerminee(){
         Iterator<Evenement> iter = evenements.iterator();
         while (iter.hasNext()){
             Evenement eve = iter.next();
             if (this.time <= eve.getDate()){
                 return false;
             }
         }
         return true;
    }

    /**
    * Avance la simulation d'un pas de temps, si elle n'est pas finie.
    */
    @Override
    public void next() {

       if (simulationTerminee()){
       }
       else{
          this.time += this.pas;
          if (this.time % (this.pas * 100) == 0) {
              System.out.println("t=" + this.time + "s");
          }
          executeEvenements();
          draw();
      }

    }

    /**
    * Replace la simulation dans les conditions initiales
    */
    @Override
    public void restart(){
      this.time = 0;
      try{
          DonneesSimulation data_init = LecteurDonnees.lire(this.donnees.fichier);
          this.donnees.RemettreInitial(data_init);
          this.positions = new Case[this.donnees.getRobots().length];
          this.reservoirs = new double[this.donnees.getRobots().length];
          for (int i =0; i<this.donnees.getRobots().length; i++){
              int lig = this.donnees.getRobots()[i].getLigne();
              int col = this.donnees.getRobots()[i].getColonne();
              this.positions[i] = this.donnees.getCarte().getTableauDeCases()[lig* this.donnees.getCarte().getNbColonnes()+col];
              this.reservoirs[i] = this.donnees.getRobots()[i].getReservoir();
          }
          draw();
          AfficherEvenements();
      }
      catch (FileNotFoundException e) {
          System.out.println("fichier " + this.donnees.fichier+ " inconnu ou illisible");
      } catch (DataFormatException e) {
          System.out.println("\n\t**format du fichier " +this.donnees.fichier+ " invalide: " + e.getMessage());
      }
    }


    /**
    * Dessine la simulation dans l'interface graphique
    */
    private void draw(){
      Carte cart = this.donnees.getCarte();
      int nb_lignes = cart.getNbLignes();
      int nb_colonnes = cart.getNbColonnes();
      int taille_ecran = 800;
      int taille_cases = taille_ecran/Math.max(nb_colonnes, nb_lignes);
      this.gui.reset();

      for (Case c : cart.getTableauDeCases()){
          c.draw_case(this.gui, taille_cases);
      }

      for (Incendie i : this.donnees.getIncendies()){
        i.draw_incendie(this.gui, taille_cases);
      }

      for (Robot r : this.donnees.getRobots()){
        r.draw_robot(this.gui, taille_cases);
      }
    }

    /**
    * Retourne la position du robot entre, en case.
    */
    public Case getPosition(Robot robot){
        Case C=null;
        for (int i = 0; i<this.donnees.getRobots().length; i++){
            if (this.donnees.getRobots()[i]==robot){
                C = this.positions[i];
                break;
            }
        }
        return C;

    }

    /**
    * Met le robot a la case rentree
    */
    public void setPosition(Robot robot, Case C){
        for (int i = 0; i<this.donnees.getRobots().length; i++){
            if (this.donnees.getRobots()[i]==robot){

                this.positions[i] = C;
                break;
            }
        }
    }

    /**
    * Retourne le reservoir du robot
    */
    public double getReservoir(Robot robot){
        double volume=0;
        for (int i = 0; i<this.donnees.getRobots().length; i++){
            if (this.donnees.getRobots()[i]==robot){

            volume = this.reservoirs[i];
            break;
            }
        }
        return volume;

    }

    /**
    * Met le volume entre dans le reservoir du robot
    */
    public void setReservoir(Robot robot, double volume){
        for (int i = 0; i<this.donnees.getRobots().length; i++){
            if (this.donnees.getRobots()[i]==robot){

                this.reservoirs[i] = volume;
                break;
            }
        }
    }
}
