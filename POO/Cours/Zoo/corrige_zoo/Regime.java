

class Regime extends TestZoo {
    private String nom;
    private int prixKilo;

    public Regime(String nom, int prixKilo) {
        this.nom = nom;
        this.prixKilo = prixKilo;
    }

    public int getPrixKilo() {
        return prixKilo;
    }

    @Override
    public String toString() {
        return nom + " (" + prixKilo + "  €/kg)";
    }

}
