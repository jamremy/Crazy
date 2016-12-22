package ennemi;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import carte.ListeDeGraphiques;
import explosion.Explosion;
import explosion.ListeDeExplosions;

public class ListeDeEnnemis extends ArrayList<Ennemi> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5755031814015112287L;

	public JSONObject allPositions() {
		JSONObject positions = new JSONObject();
		for (Ennemi e : this) {
			if (e.getY() > 0 && e.getY() < 700) {
				JSONObject graphique = new JSONObject();

				try {
					graphique.put("id", e.getId());
					graphique.put("nom", e.getNom());
					graphique.put("x", e.getX());
					graphique.put("y", e.getY());
					graphique.put("Vie", e.getVie());
					positions.accumulate("Ennemis", graphique);
				} catch (JSONException exc) {
					exc.printStackTrace();
				}
			}
		}

		return positions;
	}

	public void actualiser(long time, ListeDeGraphiques listeDeGraphiques, ListeDeExplosions listeDesExplosions) {

		for (Iterator<Ennemi> iterator = this.iterator(); iterator.hasNext();) {
			Ennemi e = iterator.next();
			if (e.getVie() <= 0) {
				listeDesExplosions.add(new Explosion("Explosion1", e.getX(), e.getY(), 100, 100));
				iterator.remove();

			} else if (e.getX() > 800 || e.getY() > 1200) {
				iterator.remove();
			} else {
				// S'il est temps d'actualiser l
				if (Math.abs(e.getTimeLastActualisation() - time) > e.getFrequenceActualisation()) {
					e.actualiser(time, listeDeGraphiques);
				}
			}
		}
	}

	public void move_all(int x, int y) {
		for (Ennemi e : this) {
			e.setX(e.getX() + x);
			e.setY(e.getY() + y);
		}
	}
}
