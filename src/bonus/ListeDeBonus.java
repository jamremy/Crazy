
package bonus;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import carte.Graphique;

public class ListeDeBonus extends ArrayList<Bonus> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1948906215179013541L;

	public JSONObject allPositions() {
		JSONObject positions = new JSONObject();
		for (Graphique g : this) {
			JSONObject graphique = new JSONObject();
			if (g.getX() > 0 && g.getX() < 800) {
				try {
					graphique.put("id", g.getId());
					graphique.put("nom", g.getNom());
					graphique.put("x", g.getX());
					graphique.put("y", g.getY());
					positions.accumulate("Bonus", graphique);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return positions;
	}

	// Actualise tout les graphiques de la liste qui ont besoin d'etre
	// actualiser
	public void Actualiser(long time) {

		for (Iterator<Bonus> iterator = this.iterator(); iterator.hasNext();) {
			Bonus b = iterator.next();
			if (b.GetDuree() == 0 || b.Get_DejaUtilise() == true) {
				iterator.remove();
			} else {

			}
		}

	}

	public void move_all(int x, int y) {
		for (Bonus b : this) {
			b.setX(b.getX() + x);
			b.setY(b.getY() + y);
		}
	}
}
