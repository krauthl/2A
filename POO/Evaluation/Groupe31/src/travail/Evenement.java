package travail;

/**
 * Classe definissant un evenement
 * contenant une date et un comparateur
 * d'evenements selon leurs dates.
 * Cela est utile lors de l'execution des evenements
 * qui se fait dans un ordre chronologique.
 *
 */

public abstract class Evenement implements Comparable<Evenement> {
		
	private long date;

	public Evenement(long date) {
		this.date = date;

	}

	public long getDate() {
		return date;
	}
	
	abstract Robot getRobot();
	
	
	/**
	 * Execute l'evenement appelant en modifiant les donnes de la simulationadequat 
	 * @param donnees
	 */
	abstract void execute(DonneesSimulation donnees);
	
	
	/**
	 * Ordre naturel: comparaison sur les dates.
	 * @return une valeur négative / nulle / positive si this est 
	 *         plus petit / égal / plus grand que e.
	 */
	@Override
	public int compareTo(Evenement e) {
		if (e == null)
			throw new NullPointerException(); 
		
		if (this.date < e.getDate()) {// this < e
			return -1;
		} else if  (this.date > e.getDate()) {// this > d
			return 1;
		} else {
			if(this.getRobot().equals(e.getRobot())) {
				return 0;
			}
			return 1; // this = d
		}
	}
}

