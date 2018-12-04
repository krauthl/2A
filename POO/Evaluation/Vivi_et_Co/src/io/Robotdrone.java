package io;
import io.*;

/**
 * Classe gerant le comportement des robots drones 
 */
public class Robotdrone extends Robot{
  /** 
   * Constucteur de classe 
   */
  public Robotdrone(int lig, int col, double vitesse_deplacement){
    super(lig, col, vitesse_deplacement);
    super.setReservoir(10000);
    super.setTypeRobot(TypeRobot.DRONE);
  }

  public long getDateremplir(){
    return 30*60;
  }

/** 
 * renvoie true si le robot peut se remplir ici, false sinon 
 */
  public boolean testRemplir(Simulateur simu, int lig, int col){
    if (simu.getPosition(this).getLigne() == lig && simu.getPosition(this).getColonne() == col){
        return true;
      }

    return false;
  }

  public double remplirReservoir(Simulateur simu, int lig, int col){
    if (testRemplir(simu, lig, col)){
      return 10000;
    }
    return 0;
  }

  public long getDateVider(double intensite){
    return ((int)(intensite))/333;
  }


  public double vider(Simulateur simu, int ligne, int colonne, double intensite, Case C){
      if (testVider(simu, ligne, colonne, C)){
        if (intensite >= super.getReservoir()){
          return 10000;
        }
        else {
          return intensite;
        }
      }
      return 0;
  }


/**
 * renvoie true si le robot peut se vider ici, false sinon 
 */
  public boolean testVider(Simulateur simu, int lig, int col, Case C){
    Incendie[] incendies = simu.donnees.getIncendies();
    boolean incendie_ici = false;
    Incendie incendie = incendies[0];

    for (int i=0; i<incendies.length; i++){
      if (incendies[i].getLigne()==lig && incendies[i].getColonne()==col){
        incendie_ici = true;
        incendie = incendies[i];
      }
    }
    if (incendie_ici){
      if (C.getLigne() == incendie.getLigne() && C.getColonne() == incendie.getColonne()){
        return true;
      }
    }
    return false;
  }

  public double getVitesse(NatureTerrain Nature){
      double vitesse = super.getVitesse();
      return vitesse;
  }

  public boolean test_deplacement(Case C){
    return true;
  }
  
}
