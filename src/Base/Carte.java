package Base;

import org.json.JSONException;
import org.json.JSONObject;

import AirCraft.Bullet;
import VaisseauEnnemi.Ennemi;
import VaisseauEnnemi.ListeDeEnnemis;
import player.ListeDeJoueurs;

public class Carte {
	private ListeDeJoueurs ldj = new ListeDeJoueurs();
	private ListeDeGraphiques ldg = new ListeDeGraphiques();
	private ListeDeEnnemis lde=new ListeDeEnnemis();
	public Carte ()
	{
		
	}
	//Actualise la liste de graphique prśent sur la map
	public void Actualiser(long time)
	{
		ldg.Actualiser(time);
		lde.Actualiser(time);
	}
	
	public JSONObject GetAllGraphiquesPosition ()
	{
		JSONObject positions = new JSONObject();
		try {
			positions.accumulate("Carte", this.ldj.allPositions());
			positions.accumulate("Carte", this.ldg.allPositions());
			positions.accumulate("Carte", this.lde.allPositions());
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
	//Detecte et traite les collision entre les different objet present sur la carte
	public void DetectCollision()
	{
		for (Graphique g :ldg )
		{
			for (Ennemi e : lde)
			{
				//si il y a une collision entre un ennemi et un graphique
				if (g.ColliDeRect(e)==true || e.ColliDeRect(g)==true)
				{
					//si ce graphique est une balle
					if (g instanceof Bullet)
					{
					//alors blesser ennemis et mettre la balle en dehors de l'ecran, celle ci sera supprimer automatiquement par la suite (cf: liste de graphique.actualiser)	
					e.Blesser((Bullet)g);
					g.SetX(-500);
					
					}
				}
			}
		}
	}

	public ListeDeJoueurs GetListeDeJoueurs()
	{
		return ldj;
	}
	
	public ListeDeEnnemis GetListeDeEnnemis ()
	{
		return lde;
	}
}
	

