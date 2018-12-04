public class Canard extends Animal {

    private String couleurPlumes;

    /**Constructeur de la classe Canard*/
    public Canard(String nom, int poids, String couleur){
        super(nom, poids); //Appel au constructeur de la classe Animal
        this.couleurPlumes = couleur;
        this.setRegime(new Regime("graines", 4));
    }

    @Override
    public void crier(){
        System.out.println(this.getNom() + " cancanne \n");
    }

    public String getCouleurPlumes(){
        return this.couleurPlumes;
    }

    public void setNbDeTache(String couleur){
        this.couleurPlumes = couleur;
    }

    @Override
    public String toString(){
        return "Le canard " + this.getCouleurPlumes() + " " + this.getNom() + " pèse " + this.getPoids() + "kg";
    }
}
