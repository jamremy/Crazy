package explosion;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import airCraft.Bullet;
import carte.Graphique;
import vaisseauEnnemi.BulletEnnemi;

public class ListeDeExplosions extends ArrayList<Explosion>{
	

	public JSONObject allPositions() {
		JSONObject positions = new JSONObject();
		for(Explosion ex : this) {
			JSONObject expl = new JSONObject();
			
			try {
				expl.put("id", ex.GetId());
				expl.put("nom", ex.GetNom());
				expl.put("x", ex.GetX());
				expl.put("y", ex.GetY());
				expl.put("Image", ex.GetNumImage());
				positions.accumulate("Explosion", expl);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return positions;
	}
	
	//Actualise tout les graphiques de la liste qui ont besoin d'etre actualiser
	public void Actualiser (long time)
	{
		
		for (Iterator<Explosion> iterator = this.iterator(); iterator.hasNext(); ) {
			Explosion exp = iterator.next();
			if (exp.GetNumImage()==exp.GetNbImage() )
			{
				iterator.remove();
			}
			else 
			{
				if (Math.abs(exp.GetTimeLastActualisation()-time)>exp.GetFrequenceActualisation())
				{
					exp.Actualiser(time);
				}
					
			}
		}
		
	}
}
