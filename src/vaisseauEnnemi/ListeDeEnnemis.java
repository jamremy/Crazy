package vaisseauEnnemi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import carte.Graphique;
import carte.ListeDeGraphiques;
import airCraft.Bullet;
import explosion.ListeDeExplosions;
import explosion.Explosion;

public class ListeDeEnnemis extends ArrayList <Ennemi> {
	public JSONObject allPositions() {
		JSONObject positions = new JSONObject();
		for(Ennemi e : this) {
			if (e.GetX()>0 && e.GetX()<700)
			{
			JSONObject graphique = new JSONObject();
			
			try {
				graphique.put("id", e.GetId());
				graphique.put("nom", e.GetNom());
				graphique.put("x", e.GetX());
				graphique.put("y", e.GetY());
				graphique.put("Vie", e.getVie());
				positions.accumulate("Ennemis", graphique);
			} catch (JSONException exc) {
				exc.printStackTrace();
			}
			}
		}
		return positions;
	}
	
	
	public void Actualiser (long time,ListeDeGraphiques ldg, ListeDeExplosions ldex)
	{

		for (Iterator<	Ennemi> iterator = this.iterator(); iterator.hasNext(); ) {
			Ennemi e = iterator.next();
			if (e.getVie()<=0 )
			{
				ldex.add(new Explosion("Explosion1",e.GetX(),e.GetY(),100,100));
				iterator.remove();
			
				//e.SetX(-5000);
				//this.add( new Ennemi("ennemi1", (int) (Math.random() * ( 800 )), (int) (Math.random() * ( 800 )), 100, 100));
			}
			else if ( e.GetX()>800 || e.GetY()>1200 )
			{
				iterator.remove();
			}
			else 
			{
				//S'il est temps d'actualiser l
				if (Math.abs(e.GetTimeLastActualisation()-time)>e.GetFrequenceActualisation())
				{
				e.Actualiser(time,ldg);
				}
			}
		}
	}


	public void move_all(int x, int y) {
		// TODO Auto-generated method stub
		for (Ennemi e :this)
		{
			e.setX(e.GetX()+x);
			e.SetY(e.GetY()+y);
		}
		
	}

}
