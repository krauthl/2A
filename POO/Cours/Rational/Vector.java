public class Vector {
    private Rational[] tableau;

    /**Création d'un vecteur de la bonne taille initialisé à zéro*/
    public Vector(int taille){
      this.tableau = new Rational[taille];
      //Initialisation à zéro : il FAUT utiliser des indices attention, sinon les
      //éléments ne sont pas encore reconnus comme étant du type Rational
      for (int i = 0; i<this.tableau.length; i++){
          this.tableau[i] = new Rational(0, 1);
      }
    }

    public Rational[] getTableau(){
      /**Retourne le tableau de rationnels*/
      return this.tableau;
    }

    public String toString(){
      /**Affiche le vecteur de rationnels*/
      String str = "[";
      Rational[] tab = this.getTableau();
      for (Rational r : tab){
        if (r==tab[tab.length -1]){
          str += r.toString() + "]";
        } else {
          str += r.toString() + " ; ";
        }
      }
      return str;
    }

    public void set(int i, Rational r){
      /**Met la valeur du ième rationnel du vecteur à r*/
      Rational[] tab = this.getTableau();
      if (i>tab.length){
        throw new IllegalArgumentException("Indice trop grand pour le tableau!");
      } else {
        tab[i] = r;
      }
    }

    public Rational get(int i){
      /**Renvoie la rationnel qui correspond à la ième composante du vecteur*/
      Rational[] tab = this.getTableau();
      if (i>tab.length){
        throw new IllegalArgumentException("Indice trop grand!");
      } else {
        return tab[i];
      }
    }

    public void mult(Rational r){
      /**Multiplie tous les éléments d'un vecteur par le rationnel r*/
      for (Rational rat : this.getTableau()){
        rat.mult(r);
      }
    }

    public void add(Vector v){
      /**Additionne deux vecteurs s'ils sont de même taille*/
      Rational[] tab1 = this.getTableau();
      Rational[] tab2 = v.getTableau();
      if (tab1.length == tab2.length){
        for (int i = 0; i<tab1.length; i++){
            tab1[i].add(tab2[i]);
        }
      } else {
        throw new IllegalArgumentException("Les deux vecteurs ne sont pas de la même taille !");
      }
    }
}
