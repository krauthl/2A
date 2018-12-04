package travail;

import java.util.zip.DataFormatException;

import Enum.NatureTerrain;

/**
 * Classe définissant une case.
 * La casse contient des coordonnees relativement à la carte 
 * et a une certaine nature parmi celles enumerees.
 */

public class Case {

    /** les coordonnees d'une case (ligne, colonne) */
    private int ligne;
    private int colonne;
    /** nature du terrain de la case: libre, foret, roche, eau, habitat */
    private NatureTerrain nature;
    
    public Case() {
    	this.ligne = 0;
    	this.colonne = 0;
    }
    public Case(int ligne, int colonne) {
    	this.ligne = ligne;
    	this.colonne= colonne;
    }
    
    public int getLigne() {
        return this.ligne;
    }
    
  
	public int getColonne() {
        return this.colonne;
    }

    public NatureTerrain getNature() {
        return this.nature;
    }
    
    
    public void setNature(NatureTerrain nature) {
		this.nature = nature;
	}
    
    
    public void setNature(String nature) throws DataFormatException {
		switch(nature) {
			case "TERRAIN_LIBRE":
				this.nature = NatureTerrain.TERRAIN_LIBRE;
				break;
			case "EAU":
				this.nature = NatureTerrain.EAU;
				break;
			case "HABITAT":
				this.nature = NatureTerrain.HABITAT;
				break;
			case "FORET":
				this.nature = NatureTerrain.FORET;
				break;
			case "ROCHE":
				this.nature = NatureTerrain.ROCHE;
				break;
			default:
				throw new DataFormatException("Terrein non Pris en charge. ");
		}
	}
    
	@Override
  	public String toString() {
  		return "Case ("+this.ligne+","+this.colonne+")";
  	}
	
    @Override
    public boolean equals(Object o) {
    	
    	if(this.ligne == ((Case)o).ligne && this.colonne == ((Case)o).colonne) {
    		return true;
    	}
    	return false;
    }
    
 
}
