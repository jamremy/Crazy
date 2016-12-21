package carte;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import airCraft.Bullet;
import vaisseauEnnemi.BulletEnnemi;

public class ListeDeGraphiques extends ArrayList<Graphique> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8912243259400120931L;

	public JSONObject allPositions() {
		JSONObject positions = new JSONObject();

		for (Graphique g : this) {
			JSONObject graphique = new JSONObject();

			if (g.getX() > 0 && g.getX() < 700) {
				try {
					graphique.put("id", g.getId());
					graphique.put("nom", g.getNom());
					graphique.put("x", g.getX());
					graphique.put("y", g.getY());
					positions.accumulate("Graphique", graphique);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return positions;
	}

	// Actualise tout les graphiques de la liste qui ont besoin d'etre
	// actualiser
	public void actualiser(long time) {

		for (Iterator<Graphique> iterator = this.iterator(); iterator.hasNext();) {
			Graphique g = iterator.next();

			// si une balle est en dehors de l'ecran alors suppression de
			// celle-ci

			if (g.getX() < 0 || g.getX() < 0 || g.getX() > 800 || g.getY() > 1200) {
				if (g instanceof Bullet) {
					iterator.remove();
				}
			} else {
				if (Math.abs(g.getTimeLastActualisation() - time) > g.getFrequenceActualisation()) {
					if (g instanceof BulletEnnemi) {
						((BulletEnnemi) g).actualiser(time);
					} else if (g instanceof Bullet) {
						((Bullet) g).actualiser(time);

					}
				}

			}
		}

	}
}
