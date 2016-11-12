package VaisseauEnnemi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import AirCraft.Bullet;
import Base.Graphique;

public class ListeDeEnnemis extends ArrayList <Ennemi> {
	public JSONObject allPositions() {
		JSONObject positions = new JSONObject();
		for(Ennemi e : this) {
			JSONObject graphique = new JSONObject();
			
			try {
				graphique.put("id", e.GetId());
				graphique.put("nom", e.GetNom());
				graphique.put("x", e.GetX());
				graphique.put("y", e.GetY());
				positions.accumulate("Ennemis", graphique);
			} catch (JSONException exc) {
				exc.printStackTrace();
			}
		}
		return positions;
	}
	
	
	public void Actualiser (long time)
	{

		for (Iterator<	Ennemi> iterator = this.iterator(); iterator.hasNext(); ) {
			Ennemi e = iterator.next();
			if (e.GetVie()<0 )
			{
				iterator.remove();
				//e.SetX(-5000);
				//this.add( new Ennemi("ennemi1", (int) (Math.random() * ( 800 )), (int) (Math.random() * ( 800 )), 100, 100));
			}
			else 
			{
				e.Actualiser(time);
			}
		}
	}

}
