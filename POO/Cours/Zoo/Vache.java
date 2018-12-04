public class Vache extends Animal {

    private int nbDeTaches;

    /**Constructeur de la classe Tigre*/
    public Vache(String nom, int poids, int nb){
        super(nom, poids); //Appel au constructeur de la classe Animal
        this.nbDeTaches = nb;
        this.setRegime(new Regime("foin", 3));
    }

    @Override
    public void crier(){
        System.out.println(this.getNom() + " meugle \n");
    }

    public int getNbDeTaches(){
        return this.nbDeTaches;
    }

    public void setNbDeTaches(int nb){
        this.nbDeTaches = nb;
    }

    @Override
    public String toString(){
        return "La vache " + this.getNom() + " pèse " + this.getPoids() + "kg et a " + this.getNbDeTaches() + " taches";
    }
}
