package bonus;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import airCraft.Bullet;
import carte.Graphique;
import vaisseauEnnemi.BulletEnnemi;
import vaisseauEnnemi.Ennemi;

public class ListeDeBonus extends ArrayList<Bonus>{
	
	
	public JSONObject allPositions() {
		JSONObject positions = new JSONObject();
		for(Graphique g : this) {
			JSONObject graphique = new JSONObject();
			
			try {
				graphique.put("id", g.GetId());
				graphique.put("nom", g.GetNom());
				graphique.put("x", g.GetX());
				graphique.put("y", g.GetY());
				positions.accumulate("Bonus", graphique);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return positions;
	}
	//Actualise tout les graphiques de la liste qui ont besoin d'etre actualiser
	public void Actualiser (long time)
	{
		
		for (Iterator<Bonus> iterator = this.iterator(); iterator.hasNext(); ) {
			Bonus b = iterator.next();
			if (b.GetDuree()==0 || b.Get_DejaUtilise()==true )
			{
				iterator.remove();
			}
			else 
			{
		
					
			}
		}
		
	}
	public void move_all(int x, int y) {
		// TODO Auto-generated method stub
		for (Bonus b:this)
		{
			b.setX(b.GetX()+x);
			b.SetY(b.GetY()+y);
		}
	}
}
