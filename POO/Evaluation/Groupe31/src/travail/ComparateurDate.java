package travail;

import java.util.Comparator;

/**
 * Classe servant à comparer deux evenements Evenement sur leur date.
 * on l'a mis dans un fichier à part pour pouvoir l'utiliser dans les autres repertoires
 */
public class ComparateurDate implements Comparator<Evenement> {

	@Override
	public int compare(Evenement e0, Evenement e1) {
		return e0.compareTo(e1);
	}
}