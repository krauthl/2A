public class Tigre extends Animal {

    /**Constructeur de la classe Tigre*/
    public Tigre(String nom, int poids){
        super(nom, poids); //Appel au constructeur de la classe Animal
        this.setRegime(new Regime("viande", 1));
    }

    @Override
    public void crier(){
        System.out.println(this.getNom() + " feule comme un gros chat rayé \n");
    }

    @Override
    public String toString(){
        return "Le tigre " + this.getNom() + " pèse " + this.getPoids() + "kg";
    }
}
