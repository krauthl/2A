public class Test {
  public static void main(String[] args){
    Rational r1 = new Rational(3, 2);
    System.out.println(r1);

    /** Test de la simplification */
    Rational r2 = new Rational(6, 4);
    System.out.println(r2);

    /** Test multiplication */
    Rational r3 = new Rational(1, 2);
    r2.mult(r3);
    System.out.println(r2);

    /** Test addition */
    Rational r4 = new Rational(1, 4);
    r4.add(r3);
    System.out.println(r4);
  }
}
