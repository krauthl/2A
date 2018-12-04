package travail;

/**
 * Classe distance utilisé dans le calcul des chemins
 * afin de pouvoir comparer des distances lors de l'application
 * de l'algorithme de calcul du plus court chemin
 *
 */
public class Distance implements Comparable<Distance> {
	private Case caseActuelle;
	private Distance DistancePere;
	private double distanceSource;
	private boolean dejaVisiter;
	
	public Distance(Case caseActuelle) {
		this.caseActuelle = caseActuelle;
		this.distanceSource = Integer.MAX_VALUE;
		this.DistancePere = null;
		this.dejaVisiter = false;
	}


	public Distance(Case caseActuelle, int i) {
		this.caseActuelle = caseActuelle;
		this.distanceSource = i;
		this.DistancePere = null;
		this.dejaVisiter = false;
	}


	public boolean isDejaVisiter() {
		return dejaVisiter;
	}


	public void setDejaVisiter(boolean dejaVisiter) {
		this.dejaVisiter = dejaVisiter;
	}


	@Override
	public int compareTo(Distance d0) {
		if (d0 == null)
			throw new NullPointerException(); 
		
		if (this.distanceSource < d0.distanceSource) {
			return -1;
		}else if (this.distanceSource > d0.distanceSource) {
			return 1;
		}
		
		else {
			
			if(this.caseActuelle.equals(d0.caseActuelle)) {
				
				return 0;
			}else {
				return -1;
			}
		} 
	}


	public Case getCaseActuelle() {
		return caseActuelle;
	}


	public void setCaseActuelle(Case caseActuelle) {
		this.caseActuelle = caseActuelle;
	}


	public Distance getDistancePere() {
		return DistancePere;
	}


	public void setDistancePere(Distance casePere) {
		this.DistancePere = casePere;
	}


	public double getDistanceSource() {
		return distanceSource;
	}


	public void setDistanceSource(double distanceSource) {
		this.distanceSource = distanceSource;
	}


	@Override
	public String toString() {
		return "Distance [caseActuelle=" + caseActuelle +  ", distanceSource="
				+ distanceSource + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if(caseActuelle.equals(((Distance)obj).caseActuelle)){
			
			return true;
		}
		return false;
	}


	
	
	
}

