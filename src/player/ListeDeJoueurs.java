package player;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import vaisseauEnnemi.Ennemi;

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
	public ArrayList<Joueur> GetListeJoueur()
	{
		return this.liste;
	}
	public void deleteJoueur(int id) {
		for(Joueur j : this.liste) {
			if(j.getId()==id) {
				liste.remove(j);
			}
		}
	}
	public void Actualiser (long time )
	{
		for (Iterator<	Joueur> iterator = this.liste.iterator(); iterator.hasNext(); ) {
			Joueur j = iterator.next();
			j.GetAvion().Actualiser();
			if (j.GetAvion().GetVie()<0 )
			{
				j.GetAvion().SetX(-150000000);
				j.GetAvion().SetY(-255580200);
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
					joueur.put("Vie", j.GetAvion().GetVie());
					joueur.put("Score", j.GetScore());
					joueur.put("angle", infos[2]);
					positions.accumulate("Joueur", joueur);
				} 
			catch (JSONException e) 
			{
				e.printStackTrace();
			}
		}
		return positions;
	}	
}
