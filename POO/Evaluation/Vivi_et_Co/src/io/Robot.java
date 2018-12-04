package io;
import gui.*;
import java.awt.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import gui.Text;

/**
* Cette classe gere toutes les donnees propres aux robots
*/
public abstract class Robot {
  private int ligne;
  private int colonne;
  private double vitesse_deplacement;
  private double reservoir;
  private TypeRobot type;
  private Etat etat;

/**
 * Constructeur de la clase
 */
  public Robot(int lig, int col, double vitesse_deplacement){
    this.ligne = lig;
    this.colonne = col;
    this.vitesse_deplacement = vitesse_deplacement;
    this.reservoir = 0; //Absurde car c'est pas lui qui remplit
    this.etat = Etat.LIBRE;
  }

// sets & gets
/**
 * retourne la ligne du robot
 */
    public int getLigne(){
        return this.ligne;
    }

/**
 * retourne la colonne du robot
 */
    public int getColonne(){
        return this.colonne;
    }

/**
 * retourne l'etat du robot
 */
    public Etat getEtat(){
        return this.etat;
    }

/**
 * setter de l'etat du robot
 */
    public void setEtat(Etat etat){
        this.etat = etat;
    }

/**
 * setter de la ligne du robot
 */
    public void setLigne(int ligne){
        this.ligne = ligne;
    }

/**
 * setter de la colonne du robot
 */
    public void setColonne(int colonne){
        this.colonne = colonne;
    }

/**
 * retourne la vitesse du robot
 */
    public double getVitesse(){
      return this.vitesse_deplacement;
    }

/**
 * retourne le reservoir du robot
 */
    public double getReservoir(){
      return this.reservoir;
    }

/**
 * setter du reservoir du robot
 */
    public void setReservoir(double reservoir){
        if (reservoir >= 0){
            this.reservoir = reservoir;
        }
        else {
            this.reservoir = - reservoir;
            // throw new IllegalArgumentException(" remplir par un truc negatif est impossible" + reservoir +  this);
        }
    }

/**
 * sette de la vitesse du robot
 */
    public void setVitesse(double vitesse){
        this.vitesse_deplacement = vitesse;
    }

/**
 * setter de la position du robot
 */
    public void setPosition(Case Case){
      this.setLigne(Case.getLigne());
      this.setColonne(Case.getColonne());
      double vitesse = 0;
      switch (this.getTypeRobot()) {
        case ROUES:
          Robotaroues Robot_roue = new Robotaroues(this.getLigne(), this.getColonne(), this.getVitesse());
          vitesse = Robot_roue.getVitesse(Case.getNature());
          break;

        case CHENILLES:
          Robotachenilles Robot_chenille = new Robotachenilles(this.getLigne(), this.getColonne(), this.getVitesse());
          vitesse = Robot_chenille.getVitesse(Case.getNature());
          break;

        case PATTES:
          Robotapattes Robot_pattes = new Robotapattes(this.getLigne(), this.getColonne(), this.getVitesse());
          vitesse = Robot_pattes.getVitesse(Case.getNature());
          break;

        case DRONE:
          Robotdrone Robot_drone = new Robotdrone(this.getLigne(), this.getColonne(), this.getVitesse());
          vitesse = Robot_drone.getVitesse(Case.getNature());
          break;

        default:
          break;

      }
      this.setVitesse(vitesse);
    }

// fin sets & gets

/**
 * renvoie true si le robot peut se rendre sur la case, false sinon
 */
    public boolean test_deplacement(Case C){
      boolean boo = false;
      switch (this.getTypeRobot()) {
        case ROUES:
          Robotaroues Robot_roue = new Robotaroues(this.getLigne(), this.getColonne(), this.getVitesse());
          boo = Robot_roue.test_deplacement(C);
          break;

        case CHENILLES:
          Robotachenilles Robot_chenille = new Robotachenilles(this.getLigne(), this.getColonne(), this.getVitesse());
          boo = Robot_chenille.test_deplacement(C);
          break;

        case PATTES:
          Robotapattes Robot_pattes = new Robotapattes(this.getLigne(), this.getColonne(), this.getVitesse());
          boo = Robot_pattes.test_deplacement(C);
          break;

        case DRONE:
          Robotdrone Robot_drone = new Robotdrone(this.getLigne(), this.getColonne(), this.getVitesse());
          boo = Robot_drone.test_deplacement(C);
          break;

        default:
          break;
      }
      return boo;
    }

/**
 * retourne le type du robot
 */
    public TypeRobot getTypeRobot(){
      return this.type;
    }

/**
 * setter du type du robot
 */
    public void setTypeRobot(TypeRobot T){
      this.type = T;
    }

/**
 * dessine le robot dans l'interface graphique
 */
    public void draw_robot(GUISimulator gui, int taille_case){
      int x = taille_case/5 + (this.getLigne())* taille_case;
      int y =  taille_case/5 + (this.getColonne())* taille_case;
      int taille = 4 * taille_case/5;
      switch (this.getTypeRobot()){
          case DRONE:
              gui.addGraphicalElement(new ImageElement(y, x, "img/drone.png", taille, taille, new Canvas()));
              break;
          case ROUES:
              gui.addGraphicalElement(new ImageElement(y, x, "img/roues.png", taille, taille, new Canvas()));
              break;
          case CHENILLES:
              gui.addGraphicalElement(new ImageElement(y, x, "img/chenilles.png", taille, taille, new Canvas()));
              break;
          case PATTES:
              gui.addGraphicalElement(new ImageElement(y, x, "img/pattes.png", taille, taille, new Canvas()));
              break;
      }
    }

/**
 * retourne la date de fin de vidage du robot
 */
    public long getDatevider(Simulateur simu){
        double volume = 0;
        int ligne = simu.getPosition(this).getLigne();
        int colonne = simu.getPosition(this).getColonne();
        Incendie[] incendies = simu.donnees.getIncendies();
        Incendie incendie = incendies[0];
        for (int i=0; i<incendies.length; i++){
          if (incendies[i].getLigne()==ligne && incendies[i].getColonne()==colonne){
            incendie = incendies[i];
          }
        }
    double intensite = incendie.getIntensite();
      long Date = 0;
      switch (this.getTypeRobot()) {
        case ROUES:
          Robotaroues Robot_roue = new Robotaroues(getLigne(), this.getColonne(), this.getVitesse());
          Date = Robot_roue.getDateVider(intensite);
          break;

        case CHENILLES:
          Robotachenilles Robot_chenille = new Robotachenilles(this.getLigne(), this.getColonne(), this.getVitesse());
          Date = Robot_chenille.getDateVider(intensite);
          break;

        case PATTES:
          Robotapattes Robot_pattes = new Robotapattes(this.getLigne(), this.getColonne(), this.getVitesse());
          Date = Robot_pattes.getDateVider(intensite);
          break;

        case DRONE:
          Robotdrone Robot_drone = new Robotdrone(this.getLigne(), this.getColonne(), this.getVitesse());
          Date = Robot_drone.getDateVider(intensite);
          break;

        default:
          break;
      }
      return Date;
    }

/**
 * retourne la date de fin de remplissage du robot
 */
    public long getDateremplir(){
      long Date = 0;
      switch (this.getTypeRobot()) {
        case ROUES:
          Robotaroues Robot_roue = new Robotaroues(getLigne(), this.getColonne(), this.getVitesse());
          Date = Robot_roue.getDateremplir();
          break;

        case CHENILLES:
          Robotachenilles Robot_chenille = new Robotachenilles(this.getLigne(), this.getColonne(), this.getVitesse());
          Date = Robot_chenille.getDateremplir();
          break;

        case PATTES:
          throw new IllegalArgumentException("On ne peut pas remplir le reservoir d'un robot a pattes");


        case DRONE:
          Robotdrone Robot_drone = new Robotdrone(this.getLigne(), this.getColonne(), this.getVitesse());
          Date = Robot_drone.getDateremplir();
          break;

        default:
          break;
      }
      return Date;
    }

/**
 * remplit le reservoir du robot
 */
    public double remplirReservoir(Simulateur simu, int ligne, int colonne){
        double volume = 0;
        switch (this.getTypeRobot()) {
            case ROUES:
                Robotaroues Robot_roues = new Robotaroues(this.getLigne(), this.getColonne(), this.getVitesse());
                volume = Robot_roues.remplirReservoir(simu, ligne, colonne);
                break;

            case CHENILLES:
                Robotachenilles Robot_chenilles = new Robotachenilles(this.getLigne(), this.getColonne(), this.getVitesse());
                volume = Robot_chenilles.remplirReservoir(simu, ligne, colonne);
                break;

            case PATTES:
                throw new IllegalArgumentException("On ne peut pas remplir le reservoir d'un robot a pattes");

            case DRONE:
                Robotdrone Robot_drone = new Robotdrone(this.getLigne(), this.getColonne(), this.getVitesse());
                volume = Robot_drone.remplirReservoir(simu, ligne, colonne);
                break;

            default:
              break;
        }
        return volume;
      }

/**
 * vide le robot de l'intensite voulue
 */
public double Vider(Simulateur simu,  int ligne, int colonne, double intensite, boolean boo){
    double volume = 0;
    Case C;
    if (boo){
        C = simu.getPosition(this);
    }
    else{
        C = simu.donnees.getCarte().getTableauDeCases()[simu.donnees.getCarte().getNbColonnes() * this.getLigne() + this.getColonne()];
    }
    switch (this.getTypeRobot()) {
      case ROUES:
        Robotaroues Robot_roue = new Robotaroues(this.getLigne(), this.getColonne(), this.getVitesse());
        volume = Robot_roue.vider(simu, ligne, colonne, intensite, C);
        break;

      case CHENILLES:
        Robotachenilles Robot_chenille = new Robotachenilles(this.getLigne(), this.getColonne(), this.getVitesse());
        volume = Robot_chenille.vider(simu, ligne, colonne, intensite, C);
        break;

      case PATTES:
        Robotapattes Robot_pattes = new Robotapattes(this.getLigne(), this.getColonne(), this.getVitesse());
        volume = Robot_pattes.vider(simu, ligne, colonne, intensite, C);
        break;

      case DRONE:
        Robotdrone Robot_drone = new Robotdrone(this.getLigne(), this.getColonne(), this.getVitesse());
        volume = Robot_drone.vider(simu, ligne, colonne, intensite, C);
        break;

      default:
        break;
    }
    return volume;
  }

    @Override
    public String toString(){
        return "Type : " +  this.getTypeRobot().toString() + ", Case = (" + this.getLigne() + ", " + this.getColonne() + ")";
    }

}
