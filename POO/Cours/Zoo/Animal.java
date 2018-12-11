public class Animal {
    private String nom;
    private int poids;
    private Regime regime;

    /**Constructeur de la classe des animaux*/
    public Animal(String nom, int poids){
        this.nom = nom;
        this.poids = poids;
        this.regime = regime;
    }

    public String toString(){
        return this.getNom() + " pèse " + this.getPoids() + "kg";
    }

    public void crier(){
        System.out.println(this.nom + " crie ...\n");
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setPoids(int poids){
        this.poids = poids;
    }

    public String getNom(){
        return this.nom;
    }

    public int getPoids(){
        return this.poids;
    }

    public void setRegime(Regime r){
        this.regime = r;
    }

    public int cout_animal(){
      if (this.regime.nom == "graines"){
          return 1;
      } else {
          return this.poids * this.regime.prix_kg;
      }
    }
}
