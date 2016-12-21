package explosion;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class ListeDeExplosions extends ArrayList<Explosion> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3608610577832821480L;

	public JSONObject allPositions() {
		JSONObject positions = new JSONObject();
		for (Explosion ex : this) {
			JSONObject expl = new JSONObject();

			try {
				expl.put("id", ex.getId());
				expl.put("nom", ex.getNom());
				expl.put("x", ex.getX());
				expl.put("y", ex.getY());
				expl.put("Image", ex.GetNumImage());
				positions.accumulate("Explosion", expl);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return positions;
	}

	// Actualise tout les graphiques de la liste qui ont besoin d'etre
	// actualiser
	public void Actualiser(long time) {

		for (Iterator<Explosion> iterator = this.iterator(); iterator.hasNext();) {
			Explosion exp = iterator.next();
			if (exp.GetNumImage() == exp.GetNbImage()) {
				iterator.remove();
			} else {
				if (Math.abs(exp.getTimeLastActualisation() - time) > exp.getFrequenceActualisation()) {
					exp.Actualiser(time);
				}

			}
		}

	}
}
