package io;

/**
 * Classe gerant les comportement des robots a pattes
 */
public class Robotapattes extends Robot {

  /**
  * Constucteur de la classe
  */
  public Robotapattes(int lig, int col, double vitesse_deplacement){
    super(lig, col, vitesse_deplacement);
    super.setReservoir(Double.POSITIVE_INFINITY);
    super.setTypeRobot(TypeRobot.PATTES);
  }

  public double getVitesse(NatureTerrain Nature){
    double vitesse = super.getVitesse();
    switch (Nature){
      case ROCHE:
      vitesse = 10;
      break;

      default:
      vitesse = 30;
      break;
    }
    return vitesse;
  }


  public long getDateremplir(){
    return 0;
  }

  public double remplirReservoir(Simulateur simu, int ligne, int colonne){
    return 0;
  }

  public long getDateVider(double intensite){

      return ((int)intensite/10)*1;
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

  public double vider(Simulateur simu, int ligne, int colonne, double intensite, Case C){

      if (testVider(simu, ligne, colonne, C)){
        return intensite;
        }
    return 0;
  }

  public double remplirReservoir(){
      return Double.POSITIVE_INFINITY;
  }


  public boolean test_deplacement(Case C){
    boolean possible = true;
    switch (C.getNature()){
      case EAU:
        possible = false;
        break;
      default:
          break;
    }
    return possible;
  }

}
