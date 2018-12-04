
package io;


/**
 * Cette classe gere les evenements lies au deversage de l'eau dans les robots
 * sur les incendie
 */
public class EvenementDeverserEau extends Evenement {

/**
 * Constucteur de la classe
 */
  public EvenementDeverserEau(Simulateur simu, Robot robot){
    super(robot, simu, robot.getDatevider(simu), TypeEvenement.DeverserEau);
    int ligne = super.getSimu().getPosition(robot).getLigne();
    int colonne = super.getSimu().getPosition(robot).getColonne();
    Incendie[] incendies = super.getSimu().donnees.getIncendies();
    Incendie incendie = incendies[0];
    for (int i=0; i<incendies.length; i++){
      if (incendies[i].getLigne()==ligne && incendies[i].getColonne()==colonne){
        incendie = incendies[i];
      }
    }
    double intensite = incendie.getIntensite();
    double reservoir = super.getSimu().getReservoir(robot);
    if (intensite == 0){
      throw new IllegalArgumentException(robot + " ne peut pas faire d'intervention ici");
    }

    double volume = robot.Vider(super.getSimu(), ligne, colonne, intensite, true);
    if (volume == 0){
      return;
    }
    super.getSimu().setReservoir(robot, reservoir - volume);
    simu.addEvenement(this);

  }

  public void execute(){
      super.execute();
      Robot robot = super.getRobot();
      int ligne = robot.getLigne();
      int colonne = robot.getColonne();
      //Trouve l'incendie concerné
      Incendie[] incendies = super.getSimu().donnees.getIncendies();
      Incendie incendie = incendies[0];
      for (int i=0; i<incendies.length; i++){
        if (incendies[i].getLigne()==ligne && incendies[i].getColonne()==colonne){
          incendie = incendies[i];
        }
      }
      double intensite = incendie.getIntensite();
      double reservoir = super.getSimu().getReservoir(robot);
      if (intensite == 0){
        throw new IllegalArgumentException(robot + " ne peut pas faire d'intervention ici");
      }

      double volume = robot.Vider(super.getSimu(), ligne, colonne, intensite, false);
      if (volume == 0){
        return;
      }
      incendie.setIntensite(intensite - volume);
      super.robot.setReservoir(reservoir - volume);

      }

      public String toString(){
          return super.toString() + "DeverserEau: " + this.robot + "deverse en (" + this.getRobot().getLigne() + ", " + this.getRobot().getColonne() + ")";
      }


  }
