public class Vector {
    private Rational[] vec;
    // nb: there is no need for a "size" attribute, which would duplicate
    // the information contained in vec.length (with a risk of incoherence
    // between the redundant informations)

	// creates vector of size Rational, initalized to 0/1
	public Vector(int size) {
		this.vec = new Rational[size];
		for (int i = 0; i < vec.length; i++) {
			// a "default" Rational 0/1 is created at each index
			this.vec[i] = new Rational(0, 1);
		}
	}

	public Vector(Rational vec[]) {
		this(vec.length); 
		for (int i = 0; i < vec.length; i++) {
			this.vec[i] = vec[i];	// shallow copy!
		}
	}

	public Vector(Vector v) {
		this(v.vec);
	}


	public int size() {
		return this.vec.length;	
	}

	public Rational get(int i) {
		return this.vec[i];
	}

	public void set(int i, Rational r) {
		this.vec[i] = r;	// shallow copy
	}


	@Override
	public String toString() {
		String s = new String("(");
		for (int i = 0; i < this.vec.length - 1; i++)
			s += get(i) + ", ";
		s += get(this.vec.length - 1) + ")";
		return s;
	}

	public void mult(Rational r) {
		for (int i = 0; i < size(); i++) {
			vec[i].mult(r);
		}
	}

	public void add(Vector v) {
		if (this.size() != v.size())
			throw new IllegalArgumentException("Vectors of different sizes.");

		for (int i = 0; i < this.size(); i++) {
			this.vec[i].add(v.get(i));
		}
	}
} 

