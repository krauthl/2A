
import java.util.LinkedList;

class Zoo {
    private String nom;
    private LinkedList<Animal> animaux;

    public Zoo(String nom) {
        this.nom = nom;
        animaux = new LinkedList<Animal>();
    }

    public int getNbAnimaux() {
        return animaux.size();
    }

    public void ajouteAnimal(Animal animal) {
        animaux.add(animal);
    }

    // fais crier tous les animaux. Bonjour l'ambiance...
    public void crier() {
        System.out.println("*** 1, 2, 3 crions:");
        for (Animal a : animaux) {
            a.crier();
        }
    }

    // affiche le cout de nourriture du zoo par jour
    public void coutNourriture() {
        System.out.println("*** Cout de nourriture par jour:");
        int coutTotal = 0;
        for (Animal a : animaux) {
            int cout = a.coutNourriture();
            coutTotal += cout;
            System.out.println("  Cout = " + cout);
        }
        System.out.println("Cout total = " + coutTotal);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Le zoo " + nom + " contient " + getNbAnimaux() + " animaux:");
        sb.append(animaux.toString());
        return sb.toString();

        //		return "le zoo " + nom + "contient:" + animaux; 
    }

}
