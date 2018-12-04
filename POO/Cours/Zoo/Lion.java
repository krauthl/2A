public class Lion extends Animal {

    /**Constructeur de la classe Lion*/
    public Lion(String nom, int poids){
        super(nom, poids); //Appel au constructeur de la classe Animal
        this.setRegime(new Regime("viande", 1));
    }

    @Override
    public void crier(){
        System.out.println(this.getNom() + " rugit \n");
    }

    @Override
    public String toString(){
        return "Le lion " + this.getNom() + " pèse " + this.getPoids() + "kg";
    }
}
