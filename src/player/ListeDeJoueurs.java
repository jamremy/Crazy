package player;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class ListeDeJoueurs {
	private ArrayList<Joueur> liste;
	
	public ListeDeJoueurs() {
		this.liste = new ArrayList<Joueur>();
	}

	public void ajouterJoueur(Joueur player) {
		liste.add(player);
	}
	
	public Joueur getJoueur(int id) {
		for(Joueur j : this.liste) {
			if(j.getId() == id) {
				return j;
			}
		}
		
		return null;
	}
	
	public ArrayList<Joueur> getListeJoueur() {
		return this.liste;
	}
	
	public void supprimerJoueur(int id) {
		for(Joueur j : this.liste) {
			if(j.getId() == id) {
				liste.remove(j);
			}
		}
	}
	
	public void actualiser (long time) {
		Iterator <Joueur> iterator = this.liste.iterator();
		Joueur j = null;
		
		while (iterator.hasNext()) {
			j = iterator.next();
			
			j.getAvion().actualiser();
			
			if (j.getAvion().getVie() < 0 ) {
				j.getAvion().setX(-150000000);
				j.getAvion().SetY(-255580200);
			}
			
		}
	}
	
    public int getNombreDeConnecte() {
		return this.liste.size();
	}

	public JSONObject allPositions() {
		JSONObject conteneur = new JSONObject();
		JSONObject colis     = new JSONObject();
		
		for(Joueur j : this.liste) {
			JSONObject joueur  = new JSONObject();
			String     infos[] = j.getEtat();
			
			try {
				joueur.put("id",     j.getId());
				joueur.put("x",      infos[0]);
				joueur.put("y",      infos[1]);
				joueur.put("vie",    j.getAvion().getVie());
				joueur.put("score",  j.getAvion().GetScore());
				joueur.put("angle",  infos[2]);
				joueur.put("pseudo", j.getPseudo());
				joueur.put("avion", j.getAvion().GetNom()); 
				
				// On met les objets dans le colis
				colis.accumulate(Integer.toString(j.getId()), joueur);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		try {
			// On met le colis dans le conteneur
			conteneur.accumulate("Joueur", colis);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return conteneur;
	}	
}
