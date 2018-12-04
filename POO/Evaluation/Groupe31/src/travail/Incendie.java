package travail;

/**
 * Classe definissant une incendie
 * contient le nombre de litres d'eau necessaires
 * pour pouvoir l'eteindre, ainsi que sa position
 * dans la carte
 *
 */
public class Incendie {
    /** case sur laquelle l'incendie se situe */
    private Case position;
    /** nombre de litres d'eau necessaires pour l'eteindre */
    private int litresEteindre;

    public void setPosition(Case position) {
      this.position = position;
    }

    public Case getPosition() {
      return this.position;
    }

    public void setLitresEteindre(int litres) {
        this.litresEteindre = litres;
    }
    public int getLitresEteindre() {
        return this.litresEteindre;
    }

    @Override
  	public String toString() {
  		return "Incendie dans la case: "+ this.getPosition().toString() +
          "ayant besoin de " +  this.getLitresEteindre() + "L pour s'éteindre.\n";
  	}
}
