package Bonus;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import AirCraft.Bullet;
import Base.Graphique;
import VaisseauEnnemi.BulletEnnemi;
import VaisseauEnnemi.Ennemi;

public class ListeDeBonus extends ArrayList<bonus>{
	
	
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
		
		for (Iterator<bonus> iterator = this.iterator(); iterator.hasNext(); ) {
			bonus b = iterator.next();
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
		for (bonus b:this)
		{
			b.SetX(b.GetX()+x);
			b.SetY(b.GetY()+y);
		}
	}
}
