public class Rational {
  private int num;
  private int denom = 1;

  /**
  Ecriture du constructeur
  */
  public Rational(int n, int d){
    this.num = n;
    if (d==0){
      throw new ArithmeticException("Erreur : Division par 0");
    } else {
      this.denom = d;
      this.simplif();
    }
  }

  public void setNum(int n){
    /**Initialise la valeur du numérateur*/
    this.num = n;
  }

  public void setDenom(int d){
    /**Initialise la valeur du dénominateur*/
    this.denom = d;
  }

  public int getNum(){
    /**
    Pour avoir accès au numérateur, qui est un attribut privé!
    */
    return this.num;
  }

  public int getDenom(){
    /**
    Idem accès dénominateur
    */
    return this.denom;
  }

  public String toString(){
    /**
    Permet d'afficher la chaine de caractères représentant r juste par un appel
    à System.out.println(r)
    */
    if (this.getDenom()==1){
      return ""+this.getNum();
    } else {
      return this.getNum()+" / "+this.getDenom();
    }
  }

  public void simplif(){
    /**
    Simplification des fractions, on commence par calculer le PGCD
    */
    int a = this.getNum();
    int b = this.getDenom();
    int r, q = 0;
    for (;;){
      r = a%b;
      q = (a-r)/b;
      if (r==0) break;
      a = b;
      b = r;
    }
    //b est le pgcd
    this.num /= b;
    this.denom /= b;
  }

  public void mult(Rational r){
    /**
    Muliplication : this prend la valeur this*r
    */
    this.num *= r.num;
    this.denom *= r.denom;
    this.simplif();
  }

  public void add(Rational r){
    /**
    Addition : this prend la valeur this+r
    */
    this.num = this.getNum()*r.denom + r.num*this.getDenom();
    this.denom = this.getDenom()*r.denom;
    this.simplif();
  }
}
