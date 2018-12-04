import java.util.ArrayList;

public class ExtensibleVector{
    private ArrayList tableau;

    /**Constructeur du vecteur extensible de rationnels*/
    public ExtensibleVector(int size){
        tableau = new ArrayList();
        for (int i = 0; i<size; i++){
            tableau.add(new Rational(0, 1));
        }
    }

    public ArrayList getTableau(){
        /**Retourne le tableau extensible*/
        return this.tableau;
    }

    public void add(Rational r){
        /**Ajoute le rationnel passé en paramètre en fin de vecteur*/
        tableau.add(r);
    }

    public String toString(){
        /**Affichage du vecteur extensible*/
        String str = "[";
        ArrayList tab = this.getTableau();
        for (int i = 0; i<tab.size(); i++){
            if (i == tableau.size()-1){
                str += tableau.get(i).toString() + "]";
            } else {
                str += tableau.get(i).toString() + " ; ";
            }
        }
        return str;
    }

    public int getDimension(){
        /**Retourne la taille du vecteur dynamique*/
        return this.getTableau().size();
    }
}
