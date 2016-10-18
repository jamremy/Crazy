import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class ListeDeJoueurs {
	private ArrayList<Joueur> liste;
	
	public ListeDeJoueurs() {
		this.liste = new ArrayList<Joueur>();
	}

	public void addJoueur(Joueur player) {
		liste.add(player);
	}
	
	public Joueur searchJoueur(int id) {
		for(Joueur j : this.liste) {
			if(j.getId() == id) {
				return j;
			}
		}
		return null;
	}
	
	public void deleteJoueur(int id) {
		for(Joueur j : this.liste) {
			if(j.getId()==id) {
				liste.remove(j);
			}
		}
	}
	
	public JSONObject allPositions() {
		JSONObject positions = new JSONObject();
		for(Joueur j : this.liste) {
			JSONObject joueur = new JSONObject();
			double infos[] = j.getPosition();
			try {
				joueur.put("id", j.getId());
				joueur.put("x", infos[0]);
				joueur.put("y", infos[1]);
				joueur.put("angle", infos[2]);
				positions.accumulate("Joueur", joueur);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return positions;
	}
	
	
}
