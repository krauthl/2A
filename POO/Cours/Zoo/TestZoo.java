public class TestZoo {
    public static void main(String[] args){
        Zoo Ensimag = new Zoo("Ensimag");
        Ensimag.ajouteAnimal(new Lion("Simba", 15));
        Ensimag.ajouteAnimal(new Tigre("Méchant tigre", 15));
        Ensimag.ajouteAnimal(new Vache("Lilas", 1000, 500));
        Ensimag.ajouteAnimal(new Canard("Donald", 0, "bleu"));
        System.out.println(Ensimag);
        Ensimag.crier();
    }
}
