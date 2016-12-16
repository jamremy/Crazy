package carte;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import airCraft.Bullet;
import vaisseauEnnemi.BulletEnnemi;

public class ListeDeGraphiques extends ArrayList<Graphique> {

	public JSONObject allPositions() {
		JSONObject positions = new JSONObject();
		for(Graphique g : this) {
			JSONObject graphique = new JSONObject();
			
			try {
				graphique.put("id", g.GetId());
				graphique.put("nom", g.GetNom());
				graphique.put("x", g.GetX());
				graphique.put("y", g.GetY());
				positions.accumulate("Graphique", graphique);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return positions;
	}
	//Actualise tout les graphiques de la liste qui ont besoin d'etre actualiser
	public void Actualiser (long time)
	{
		
		for (Iterator<	Graphique> iterator = this.iterator(); iterator.hasNext(); ) {
			Graphique g = iterator.next();
			//si une balle est en dehors de l'ecran alors suppression de celle-ci 
		
			if (g.GetX()<0 ||g.GetX()<0 || g.GetX()>800 || g.GetY()>800 )
			{
				if (g instanceof Bullet)
				{
				iterator.remove();
				}
			}
			else 
			{
				if (Math.abs(g.GetTimeLastActualisation()-time)>g.GetFrequenceActualisation())
				{
				if (g instanceof BulletEnnemi)
				{
					((BulletEnnemi)g).Actualiser(time);
				}
				else if (g instanceof Bullet)
				{
					((Bullet)g).Actualiser(time);
				}
				}
					
			}
		}
		
	}
}

