package io;
import java.io.*;
import java.util.*;

/**
* Cette classe gere les evenements qui se deroulent au cours de la simulation
*/
public abstract class Evenement{
  protected Robot robot;
  private long dateFin;
  private Simulateur simu;
  private TypeEvenement typeEvenement;


  /**
  * Constructeur de la classe Evenement
  */
public Evenement(Robot robot, Simulateur simulation, long dateFin, TypeEvenement type){
    this.simu = simulation;
    this.robot = robot;
    this.setDate(dateFin);
    this.robot.setEtat(Etat.OCCUPE);
    this.typeEvenement = type;
  }


  /**
  * Execute l'evenement.
  */
  public void execute(){
       long dateMax = 0;
       Iterator<Evenement> iter = simu.evenements.iterator();
       int taille = simu.evenements.size();
       Evenement[] copie = new Evenement[taille];
       for (int indice = 0; indice< taille; indice++){
           copie[indice] = iter.next();
       }
       for (int indice = 0; indice < taille; indice++){
           Evenement eve = copie[indice];
           if ( this.getRobot() == eve.getRobot()){
               if ( dateMax < eve.getDate()){
                 dateMax = eve.getDate();
               }
           }
       }
       if (dateMax <= this.simu.time + this.simu.pas){
          this.getRobot().setEtat(Etat.LIBRE);
       }
  }


  @Override
  public String toString(){
      return "Evenement : date de fin = " + this.getDate() + ", de type : ";
  }

  // sets & gets

  /**
  * Calcule la date d'execution de l'evenement
  */
  public void setDate(long Date){
      long dateMax = 0;
      Iterator<Evenement> iter = simu.evenements.iterator();
      int taille = simu.evenements.size();
      Evenement[] copie = new Evenement[taille];
      for (int indice = 0; indice< taille; indice++){
          copie[indice] = iter.next();
      }
      for (int indice = 0; indice < taille; indice++){
          Evenement eve = copie[indice];
          if ( this.getRobot() == eve.getRobot()){
              if ( dateMax < eve.getDate()){
                  dateMax = eve.getDate();
              }
          }
      }
      this.dateFin = Date + dateMax;
  }

/**
 * retourne la simulation
 */
  public Simulateur getSimu(){
      return this.simu;
  }

/**
 * setter de simulation
 */
  public void setSimu(Simulateur simulateur){
      this.simu = simulateur;
  }

/**
 * retourne le robot concerne par l'evenement
 */
  public Robot getRobot(){
      return this.robot;
  }

/**
 * retourne la date a laquelle s'execute l'evenement
 */
  public long getDate(){
    return this.dateFin;
  }

/**
 * retourne le type de l'evenement
 */
  public TypeEvenement getTypeEvenement(){
      return this.typeEvenement;
  }

/**
 * setter du type d'evenement
 */
  public void setTypeEvenement(TypeEvenement type){
      this.typeEvenement = type;
  }

}
