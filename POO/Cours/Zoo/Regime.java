public class Regime {
    private String nom;
    private int prix_kg;

    /*Constructeur de régime*/
    public Regime(String nom, int prix){
        this.nom = nom;
        this.prix_kg = prix;
    }

    public String getNomRegime(){
        return this.nom;
    }

    public int getPrixRegime(){
        return this.prix_kg;
    }
}
