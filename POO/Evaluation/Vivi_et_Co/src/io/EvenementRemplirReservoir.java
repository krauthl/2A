package io;

/**
 * Cette classe gere les evenements liess aux remplissage du reservoir du robot 
 */
public class EvenementRemplirReservoir extends Evenement {

/**
 * Constucteur de la classe 
 */
  public EvenementRemplirReservoir(Simulateur simu, Robot robot){
    super(robot, simu, robot.getDateremplir(), TypeEvenement.remplirReservoir);
    double volume = robot.remplirReservoir(super.getSimu(), super.getSimu().getPosition(robot).getLigne(), super.getSimu().getPosition(robot).getColonne());
    super.getSimu().setReservoir(robot, volume);
    simu.addEvenement(this);
  }

  public void execute(){
    super.execute();
    int ligne = super.getRobot().getLigne();
    int colonne = super.getRobot().getColonne();
    double volume = super.getRobot().remplirReservoir(super.getSimu(), ligne, colonne);
    if (volume > 0){
        this.robot.setReservoir(volume);
    }
  }

  public String toString(){
      return super.toString() + ": remplirReservoir: fais le plein en (" + this.getRobot().getLigne() + ", " + this.getRobot().getColonne() + ")";
  }
}
