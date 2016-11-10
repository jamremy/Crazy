package Base;

import org.json.JSONException;
import org.json.JSONObject;

import player.ListeDeJoueurs;

public class Carte {
	private ListeDeJoueurs ldj = new ListeDeJoueurs();
	private ListeDeGraphiques ldg = new ListeDeGraphiques();
	public Carte ()
	{
		
	}
	//Actualise la liste de graphique prśent sur la map
	public void Actualiser(long time)
	{
		ldg.Actualiser(time);
	}
	
	public JSONObject GetAllGraphiquesPosition ()
	{
		JSONObject positions = new JSONObject();
		try {
			positions.accumulate("Carte", this.ldj.allPositions());
			positions.accumulate("Carte", this.ldg.allPositions());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return positions;
	}
	
	public ListeDeGraphiques GetListeDeGraphiques()
	{
		return ldg;
	}

	public ListeDeJoueurs GetListeDeJoueurs()
	{
		return ldj;
	}
}

