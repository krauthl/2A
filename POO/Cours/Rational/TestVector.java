public class TestVector {
    public static void main(String[] args){

        /**Premier test : sans faire de copie*/
        Vector v = new Vector(2);
        System.out.println(v);
        Rational a = new Rational(2, 3);
        v.set(0, a);
        System.out.println(v);
        Rational b = new Rational(3, 2);
        a.mult(b);
        System.out.println(v);
        System.out.println("\n");

        /**Deuxième test*/
        Vector vect = new Vector(2);
        vect.set(0, new Rational(1, 2));
        vect.set(1, new Rational(1, 2));
        System.out.println(vect);
        Rational a1 = vect.get(0);
        a1.mult(new Rational(1, 3));
        System.out.println(vect);
        System.out.println("\n");

        /**Test multiplication par un rationnel*/
        Vector vec = new Vector(2);
        vec.set(0, new Rational(1, 2));
        vec.set(1, new Rational(1, 2));
        vec.mult(new Rational(2, 1));
        System.out.println(vec);
        System.out.println("\n");

        /**Test addition vecteurs*/
        Vector new_vec = new Vector(2);
        new_vec.set(0, new Rational(1, 3));
        new_vec.set(1, new Rational(1, 3));
        vec.add(new_vec);
        System.out.println(vec);
        System.out.println("\n");

        /**Test vecteur extensible*/
        ExtensibleVector extvect = new ExtensibleVector(2);
        extvect.add(new Rational(1, 3));
        System.out.println(extvect);
        System.out.println(extvect.getDimension());
    }
}
