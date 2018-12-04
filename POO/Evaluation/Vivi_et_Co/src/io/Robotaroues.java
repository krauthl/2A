package io;

/** 
 * Classe gerant les comportements des robots a roues 
 */
public class Robotaroues extends Robot {
  /** 
   * Constucteur de la classe 
   */
  public Robotaroues(int lig, int col, double vitesse_deplacement){
    super(lig, col, vitesse_deplacement);
    super.setReservoir(5000);
    super.setTypeRobot(TypeRobot.ROUES);
  }


  public double getVitesse(NatureTerrain Nature){
    double vitesse = super.getVitesse();
    switch (Nature){
        case TERRAIN_LIBRE:
          break;

        case HABITAT:
          break;

        default:
          vitesse = 0;
          break;
    }
    return vitesse;

  }

  public long getDateremplir(){
    return 10*60;
  }

  public double remplirReservoir(Simulateur simu, int lig, int col){

    if (testRemplir(simu, lig, col)){
      return 5000;
    }
    return 0;
  }

/** 
 * test si le robot a roues peut se remplir ici 
 */
  public boolean testRemplir(Simulateur simu, int lig, int col){
      boolean test1 = (simu.getPosition(this).getLigne() == lig+1)&&(simu.getPosition(this).getColonne() == col);
      boolean test2 = (simu.getPosition(this).getLigne() == lig) && (simu.getPosition(this).getColonne() == col-1);
      boolean test3 = (simu.getPosition(this).getLigne() == lig) && (simu.getPosition(this).getColonne() == col+1);
      boolean test4 = (simu.getPosition(this).getLigne() == lig-1) && (simu.getPosition(this).getColonne() == col);
      boolean test5 = (simu.getPosition(this).getLigne() == lig) && (simu.getPosition(this).getColonne() == col);

      return (test1 || test2 ||test3 || test4 ||test5);

      }


  public long getDateVider(double intensite){

        if (intensite >= super.getReservoir()){
          return 5*50;
        }
        else {
          return 5*((int)intensite/100);
        }

  }

  public double vider(Simulateur simu, int ligne, int colonne, double intensite, Case C){
    if (testVider(simu, ligne, colonne, C)){
      if (intensite >= super.getReservoir()){
        return (double) 50*100;
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

      boolean test = (C.getLigne() == incendie.getLigne()) && (C.getColonne() == incendie.getColonne());
      return test;
    }
    return false;
  }

  public boolean test_deplacement(Case C){
    boolean possible = false;
    switch (C.getNature()){
      case TERRAIN_LIBRE:
          possible = true;
          return possible;
      case HABITAT:
          possible = true;
          return possible;
      default:
          break;
    }
    return possible;
  }


}
