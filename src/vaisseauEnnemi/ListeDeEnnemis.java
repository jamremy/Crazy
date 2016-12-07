package vaisseauEnnemi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import airCraft.Bullet;
import base.Graphique;
import base.ListeDeGraphiques;
import explosion.ListeDeExplosions;
import explosion.Explosion;

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
				graphique.put("Vie", e.GetVie());
				positions.accumulate("Ennemis", graphique);
			} catch (JSONException exc) {
				exc.printStackTrace();
			}
		}
		return positions;
	}
	
	
	public void Actualiser (long time,ListeDeGraphiques ldg, ListeDeExplosions ldex)
	{

		for (Iterator<	Ennemi> iterator = this.iterator(); iterator.hasNext(); ) {
			Ennemi e = iterator.next();
			if (e.GetVie()<=0 )
			{
				ldex.add(new Explosion("Explosion1",e.GetX(),e.GetY(),100,100));
				iterator.remove();
			
				//e.SetX(-5000);
				//this.add( new Ennemi("ennemi1", (int) (Math.random() * ( 800 )), (int) (Math.random() * ( 800 )), 100, 100));
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
			e.SetX(e.GetX()+x);
			e.SetY(e.GetY()+y);
		}
		
	}

}
