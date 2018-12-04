

public class TestZoo {

    public static void main(String[] args) {
        testZooHeritage();
    }

    private static void testZooHeritage() {
        Zoo ensimag = new Zoo("Ensimag avec Heritage");
        ensimag.ajouteAnimal(new Canard("Catherine", 10, "rouge"));
        ensimag.ajouteAnimal(new Vache("Sonia", 20, 0));
        ensimag.ajouteAnimal(new Vache("Matthieu", 40, 17));
        ensimag.ajouteAnimal(new Canard("Sylvain", 50, "\"noir tuxien\""));
        ensimag.ajouteAnimal(new Vache("Nicolas", 60, 12));
        ensimag.ajouteAnimal(new Vache("Seb", 70, 88));

        System.out.println(ensimag);
        ensimag.crier();
        ensimag.coutNourriture();
    }
}
