package io;
/**
 * Classe gerant le comportement du robot a chenilles
 */
public class Robotachenilles extends Robot {

  /**
   * Constucteur de la classe
   */
  public Robotachenilles(int lig, int col, double vitesse_deplacement){
    super(lig, col, vitesse_deplacement);
    super.setReservoir(2000);
    super.setTypeRobot(TypeRobot.CHENILLES);
  }

  public double getVitesse(NatureTerrain Nature){
    double vitesse = super.getVitesse();
    switch (Nature){
        case FORET:
          if (vitesse > 50){vitesse/=2;}
          break;

        default:
           if (vitesse < 50){vitesse *= 2;}
          break;
    }

    return vitesse;
  }



  public long getDateremplir(){
    return 5*60;
  }

/**
 * test si le robot est bien place pour se remplir
 */
  public boolean testRemplir(Simulateur simu, int lig, int col){
      boolean test1 = (simu.getPosition(this).getLigne() == lig+1)&&(simu.getPosition(this).getColonne() == col);
      boolean test2 = (simu.getPosition(this).getLigne() == lig) && (simu.getPosition(this).getColonne() == col-1);
      boolean test3 = (simu.getPosition(this).getLigne() == lig) && (simu.getPosition(this).getColonne() == col+1);
      boolean test4 = (simu.getPosition(this).getLigne() == lig-1) && (simu.getPosition(this).getColonne() == col);
      boolean test5 = (simu.getPosition(this).getLigne() == lig) && (simu.getPosition(this).getColonne() == col);

      return (test1 || test2 ||test3 || test4 ||test5);

      }

  public double remplirReservoir(Simulateur simu, int ligne, int colonne){
    if (testRemplir(simu, ligne, colonne)){
      return 2000;
    }
    return 0;
  }

  public long getDateVider(double intensite){
      if (intensite >= super.getReservoir()){
          return 5*20;
      }
      else{
          return 5*((int)intensite/100);
      }
  }

  public double vider(Simulateur simu, int ligne, int colonne, double intensite, Case C){
    if (testVider(simu, ligne, colonne, C)){
        if (intensite >= super.getReservoir()){
            return 20*100;
        }
        else{
            return intensite;
        }
    }
    return 0;
  }

/**
 * test si le robot peut se vider sur cette case
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
    boolean possible = true;
    switch (C.getNature()){
      case EAU:
          possible = false;
          break;
      case ROCHE:
          possible = false;
          break;
      default:
          break;
    }
    return possible;
  }
}
