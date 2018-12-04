package io;
import io.*;
import gui.*;
import java.awt.*;


import java.util.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe responsable de l'etablissement des strategies mises en place
 * pour eteindre les incendies
 */
public class ChefPompier {
    public Simulateur simu;
    public boolean[] incendiesAffectes;

    /**
     * Constructeur de la classe
     */
    public ChefPompier(Simulateur simu){
        this.simu = simu;
        this.incendiesAffectes = new boolean[simu.donnees.getIncendies().length];
        for (int i =0; i<this.incendiesAffectes.length; i++){
            this.incendiesAffectes[i] =false;
        }
    }

    /**
    * Retourne le point d'eau le plus proche de de la case arrivee,
    * accessible par le robot.
    */
    private Chemin trouverPointDeau(Case arrivee, Robot robo){
         LinkedList<Case> casesDeau = new LinkedList<Case>();
         int nb_cases = this.simu.donnees.getCarte().getNbLignes() * this.simu.donnees.getCarte().getNbColonnes();
         for (int c=0; c < nb_cases; c++){
             Case current = this.simu.donnees.getCarte().getTableauDeCases()[c];
             if (current.getNature() == NatureTerrain.EAU){
                 if (robo.getTypeRobot() == TypeRobot.DRONE){
                         casesDeau.add(current);
                 } else{
                     for (Direction dir:Direction.values()){
                         if (this.simu.donnees.getCarte().voisinExiste(current, dir)){
                             Case voisine = this.simu.donnees.getCarte().getVoisin(current, dir);
                             if (robo.test_deplacement(voisine)){
                                 casesDeau.add(voisine);
                             }
                         }
                     }
                 }
            }
        }
         long candidat = -1;
         Chemin chem_candidat = null;
         Iterator iter = casesDeau.descendingIterator();
         while (iter.hasNext()){
             Case eau = (Case) iter.next();
             Chemin chem  = new Chemin(arrivee, eau, robo, this.simu);
             if ((chem.getTemps() < candidat) | (candidat == -1)){
                 candidat = chem.getTemps();
                 chem_candidat = chem;
             }
         }
         return chem_candidat;
    }

    /**
    * Fais aller le robot a l'incendie, et l'eteindre.
    */
    private void traiterIncendie(Chemin chem, Incendie incendie){
      double reservoir = simu.getReservoir(chem.getRobot());
      double intensite = incendie.getIntensite();
      Chemin pointEau = trouverPointDeau(chem.getArrivee(), chem.getRobot());
      Case case_incendie = chem.getArrivee();
      chem.deplacement();




      int nombre_vidages= 0;
      if (intensite>=reservoir && chem.getRobot().getTypeRobot()!=TypeRobot.PATTES){
          nombre_vidages = (int)intensite/(int)reservoir;
          if (nombre_vidages*(int)reservoir != intensite){
              nombre_vidages+=1;
          }
      }
      else {
          nombre_vidages = 1;
      }

      Chemin retour = new Chemin(pointEau.getArrivee(), case_incendie, chem.getRobot(), this.simu);
      for (int i = 0; i<nombre_vidages; i++) {
          EvenementDeverserEau vider = new EvenementDeverserEau(simu, chem.getRobot());
          if (chem.getRobot().getTypeRobot() != TypeRobot.PATTES){
              pointEau.deplacement();
              EvenementRemplirReservoir remplir = new EvenementRemplirReservoir(simu, chem.getRobot());
              retour.deplacement();
          }
      }



    }

    /**
    * Assigne les robots aux incendies de la carte selon l'argorithme
    * propose.
    */
    public void proposer_incendie_naif(){
        Incendie[] incendies =this.simu.donnees.getIncendies();
        Robot[] robots =this.simu.donnees.getRobots();
        for (int incendie_indice = 0; incendie_indice < this.incendiesAffectes.length; incendie_indice++){
            for (int robot_indice = 0; robot_indice < this.simu.donnees.getRobots().length; robot_indice++){
              if (!this.incendiesAffectes[incendie_indice]){
                if (robots[robot_indice].getEtat() == Etat.LIBRE){
                    Carte map =this.simu.donnees.getCarte();
                    Case depart = map.getTableauDeCases()[map.getNbColonnes()*this.simu.getPosition(robots[robot_indice]).getLigne() + this.simu.getPosition(robots[robot_indice]).getColonne()];
                    Case arrivee = map.getTableauDeCases()[map.getNbColonnes()*incendies[incendie_indice].getLigne() + incendies[incendie_indice].getColonne()];
                    Chemin chem = new Chemin(depart, arrivee, robots[robot_indice],this.simu);
                    if (chem.possible){
                      this.incendiesAffectes[incendie_indice] = true;
                      this.traiterIncendie(chem, incendies[incendie_indice]);
                    }
                    else{
                      continue;
                    }
                }
                else{
                  continue;
                }
              }
          else{
            continue;
          }
        }

        }
    }


    /**
    * Assigne les robots aux incendies selon le deuxieme algorithme propose
    */
    public void proposer_incendie_evolue(){
        Incendie[] incendies = this.simu.donnees.getIncendies();
        Robot[] robots = this.simu.donnees.getRobots();
        Robot candidat = null;

        for (int incendie_indice = 0; incendie_indice < this.incendiesAffectes.length; incendie_indice++){
          Chemin chemin_candidat = null;
            for (int robot_indice = 0; robot_indice < this.simu.donnees.getRobots().length; robot_indice++){
              if (!this.incendiesAffectes[incendie_indice] & robots[robot_indice].getEtat()!= Etat.INUTILE){
              long tempsmin = -1;
                if (robots[robot_indice].getEtat() == Etat.LIBRE){
                    Carte map =this.simu.donnees.getCarte();
                    Case depart = map.getTableauDeCases()[map.getNbColonnes()*this.simu.getPosition(robots[robot_indice]).getLigne() + this.simu.getPosition(robots[robot_indice]).getColonne()];
                    Case arrivee = map.getTableauDeCases()[map.getNbColonnes()*incendies[incendie_indice].getLigne() + incendies[incendie_indice].getColonne()];
                    Chemin chem = new Chemin(depart, arrivee, robots[robot_indice],this.simu);
                    if (chem.possible){
                      if ((tempsmin>chem.getTemps()) | tempsmin == -1 ){
                        chemin_candidat = chem;
                        candidat = robots[robot_indice];
                        tempsmin = chem.getTemps();
                      }
                      else{
                          continue;
                      }
                    }
                    else{
                      continue;
                    }
                }
                else{
                  continue;
                }

            if (chemin_candidat != null){
              this.incendiesAffectes[incendie_indice] = true;
              this.traiterIncendie(chemin_candidat, incendies[incendie_indice]);
            }
           }
          else{
              continue;
            }
        }
       }
       for (int indice_robot = 0; indice_robot < robots.length ; indice_robot++){
           if (robots[indice_robot].getEtat() == Etat.LIBRE){
               //Si le robot n'a pas de chemin au dernier incendie, il ne sert plus
               robots[indice_robot].setEtat(Etat.INUTILE);
           }
       }
      }

      /**
      * Eteind tous les incendies de la carte.
      */
      public void extinction(){
          boolean fini = true;
          int intensite = 0;
          boolean afficher = true;
          while(fini | intensite != 0 ){
              this.proposer_incendie_evolue();
              fini = false;
              intensite = 0;
              for (int i=0; i < incendiesAffectes.length; i++){
                  fini |= !incendiesAffectes[i];
                  intensite += this.simu.donnees.getIncendies()[i].getIntensite();
              }
              if (afficher){
                  System.out.println(" Vous pouvez appuyer sur lecture ! ");
                  afficher = false;
              }
          }
          System.out.println("Simulation Terminée après " + this.simu.time + "s");
      }

    }
