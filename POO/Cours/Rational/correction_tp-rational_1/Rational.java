/**
 * This Rational class represents rational numbers.
 * <p> All Rational number are stored using their irreductibled
 * (simplified) form, and with a positive denominator.</p>
 *
 * All Rational numbers are valid: their denominator is never Zero.
 * @author      Equipe pédagogique Ensimag - POO
 * @version     1
 */
public class Rational {
    private int num;
    private int denom = 1;

    /**
     * Initializes a newly created Rational with the value
     * num/denom, then simplifies this rational.
     *
     * @param  n numerator
     * @param  d denominator
     * @throws ArithmeticException if denom is zero
     */
    public Rational(int num, int denom) {
        this.num  = num;
        setDenom(denom);
    }

    /**
     * Initializes a newly created Rational with the value n/1.
     * @param  n numerator
     */
    public Rational(int n) {
        this(n, 1);
    }

    /**
     * Copy constructor: initializes the newly created Rational
     * with the numerator and denominator of the argument.
     */
    public Rational(Rational other) {
        this(other.num, other.denom);
    }

    /**
     * Returns the numerator.
     * @return numerator of this rational
     */
    public int getNum() {
        return this.num;
    }

    /**
     * Returns the denominator.
     * @return denominator of this rational
     */
    public int getDenom() {
        return this.denom;
    }

    /**
     * Sets the numerator, then simplifies the rational.
     * @param n new numerator value
     */
    public void setNum(int n) {
        this.num = n;
        this.simplify();
    }

    /**
     * Sets the denominator, then simplifies the rational.
     * @param d new denominator value
     * @throws ArithmeticException if d is zero
     */
    public void setDenom(int d) {
        if (d == 0) {
            throw new ArithmeticException("Division by zero...");
        }
        this.denom = d;
        this.simplify();
    }


    /**
     * Converts this rational value to double.
     * @param n new numerator value
     * @return the double floating point value closest to the
     * rational value represented by this rational.
     */
    public double toDouble() {
        return (double) num / denom;
    }

    /**
     * Multiplies this with r, then simplifies the rational.
     */
    public void mult(Rational r) {
        this.num = this.getNum() * r.getNum();
        this.denom = this.getDenom() * r.getDenom();
        this.simplify();
    }

    /**
     * Multiplies two rationals, then returns the result.
     * This a class method, used this way:
     *     Rational res = Rational.mult(r1, r2);
     */
    public static Rational mult(Rational r1, Rational r2) {
    	Rational res = new Rational(r1.num * r2.num, r1.denom * r2.denom);
        res.simplify();
        return res;
    }

    /**
     * Adds r to this, then simplifies the rational.
     */
    public void add(Rational r) {
        this.num = r.getDenom() * this.getNum() + this.getDenom() * r.getNum();
        this.denom = r.getDenom() * this.getDenom();
        this.simplify();
    }


    private void simplify() {
        int gcd = Rational.gcd(this.num, this.denom);
        this.num /= gcd;
        this.denom /= gcd;
        if (this.denom < 0) {
            this.num = -this.num;
            this.denom = -this.denom;
        }
    }

    private static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    @Override
	public boolean equals(Object o) {
        return (o instanceof Rational) &&
            (((Rational) o).getNum() == this.getNum()) &&
            (((Rational) o).getDenom() == this.getDenom());
    }

    @Override
	public String toString() {
        return this.getNum() +
            (this.getDenom() != 1 ? " / " + this.getDenom() : "");
    }
}
