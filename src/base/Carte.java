package base;

import java.io.BufferedReader;
import java.io.FileReader;

import org.json.JSONException;
import org.json.JSONObject;

import airCraft.Bullet;
import bonus.Bonus;
import bonus.ListeDeBonus;
import explosion.ListeDeExplosions;
import player.Joueur;
import player.ListeDeJoueurs;
import vaisseauEnnemi.BulletEnnemi;
import vaisseauEnnemi.Ennemi;
import vaisseauEnnemi.ListeDeEnnemis;

public class Carte {
	private ListeDeJoueurs    listeJoueurs          = new ListeDeJoueurs();
	private ListeDeGraphiques listeGraphiques       = new ListeDeGraphiques();
	private ListeDeEnnemis    listeEnnemis          = new ListeDeEnnemis();
	private ListeDeExplosions listeExplosions       = new ListeDeExplosions();
	private ListeDeBonus      listeBonus            = new ListeDeBonus();
	long                      TimeLastActualisation = 0;
	char                      BufferMap[][]         = new char[1000][];

	public Carte(String cheminCarte) {
		int i = 0;
		
		// Chargement du contenu de la carte
		try {		
			//InputStream       ips   = new FileInputStream(cheminCarte);			
			//InputStreamReader ipsr  = new InputStreamReader(ips);
			BufferedReader    br    = new BufferedReader(new FileReader(cheminCarte));
			String            ligne = null;
			
			while ((ligne = br.readLine()) != null) {
				

				//System.out.println("chaine : " + chaine);
				
				if (i < 1000) {
					BufferMap[i] = ligne.toCharArray();
					i++;
				}
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Creation des graphiques associes a la carte
		for (i = 0; i < BufferMap.length; i++) {
			for (int j = 0; j < BufferMap[i].length; j++) {				
				if (BufferMap[i][j] != 'A') {
					if (BufferMap[i][j] == 'B') {
						this.getListeEnnemis().add(new Ennemi("ennemi1", i * -80, j * 80, 100, 100));
					}
					else if (BufferMap[i][j] == 'S') {
						this.getListeBonus().add(new Bonus("Sante", i * -80, j * 80, 100, 100));
					}
					else if (BufferMap[i][j] == 'T') {
						this.getListeBonus().add(new Bonus("BallePerforante", i * -80, j * 80, 100, 100));
					}
					else if (BufferMap[i][j] == 'M') {
						this.getListeBonus().add(new Bonus("Missile", i * -80, j * 80, 100, 100));
					}
					else if (BufferMap[i][j] == '3') {
						this.getListeBonus().add(new Bonus("Ballefoistrois", i * -80, j * 80, 100, 100));
					}
					else if (BufferMap[i][j] == 'O') {
						this.getListeBonus().add(new Bonus("Bouclier", i * -80, j * 80, 100, 100));
					}
				}
			}
		}
	}

	// Actualise la liste de graphique prśent sur la map
	public void Actualiser(long time) {
		if (Math.abs(time - TimeLastActualisation) > 50) {
			getListeEnnemis().move_all(5, 0);
			getListeBonus().move_all(5, 0);
			TimeLastActualisation = time;
		}
		
		System.out.println(time - TimeLastActualisation);

		getListeGraphiques().Actualiser(time);
		getListeEnnemis().Actualiser(time, getListeGraphiques(), getListeExplosions());
		getListeJoueurs().actualiser(time);
		getListeBonus().Actualiser(time);
		getListeExplosions().Actualiser(time);

	}

	public JSONObject getEtat() {
		JSONObject positions = new JSONObject();
		
		try {
			positions.accumulate("Carte", this.getListeJoueurs().allPositions());
			positions.accumulate("Carte", this.getListeGraphiques().allPositions());
			positions.accumulate("Carte", this.getListeEnnemis().allPositions());
			positions.accumulate("Carte", this.getListeExplosions().allPositions());
			positions.accumulate("Carte", this.getListeBonus().allPositions());
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return positions;
	}

	public ListeDeGraphiques GetListeDeGraphiques() {
		return getListeGraphiques();
	}

	// Detecte et traite les collision entre les different objet present sur la carte
	public void DetectCollision() {
		for (Bonus bonusActuel : getListeBonus()) {
			for (Joueur j : getListeJoueurs().getListeJoueur()) {
				// en cas de collision entre un avion et un bonus on affecte ce bonus a la liste des bonus de l'avion
				if (bonusActuel.ColliDeRect(j.getAvion()) == true) {
					j.getAvion().GetMesBonus().add(bonusActuel);
					bonusActuel.Set_DejaUtilise(true);
				}
			}
		}

		for (Graphique graphiqueActuel : getListeGraphiques()) {
			for (Joueur joueursActuel : getListeJoueurs().getListeJoueur()) {
				// si il y a une collision entre un avion et un graphique present sur la map
				if (graphiqueActuel.ColliDeRect(joueursActuel.getAvion()) == true) {
					// sil sagit d'une balle ennemis
					if (graphiqueActuel instanceof BulletEnnemi) {
						// blesser l'avion
						joueursActuel.getAvion().Blesser((BulletEnnemi) graphiqueActuel);
						// Detruire le balle
						graphiqueActuel.setX(-500);
					}
				}
			}
		}
		
		for (Ennemi ennemiActuel : getListeEnnemis()) {
			for (Joueur j : getListeJoueurs().getListeJoueur()) {
				// en cas de collision entre un ennemi et un avion on detruit lennemi et on retire des point de vie a
				// l'avion
				if (ennemiActuel.ColliDeRect(j.getAvion()) == true) {
					ennemiActuel.Blesser(new Bullet("DeathBullet", 0, 0, 100, 100, 20));
					j.getAvion().Blesser(new Bullet("BallePerforante", 0, 0, 100, 100, 20));
				}
			}
		}

		for (Graphique graphiqueActuel : getListeGraphiques()) {
			for (Ennemi ennemiActuel : getListeEnnemis()) {
				// si il y a une collision entre un ennemi et un graphique present sur la map
				if (graphiqueActuel.ColliDeRect(ennemiActuel) == true || ennemiActuel.ColliDeRect(graphiqueActuel) == true) {
					if (graphiqueActuel instanceof BulletEnnemi) {

					}
					// sil sagit d'une balle
					else if (graphiqueActuel instanceof Bullet) {
						// blesser l'ennemis
						ennemiActuel.Blesser((Bullet) graphiqueActuel);
						// Detruire le balle
						graphiqueActuel.setX(-500);

					}
				}
			}
		}
	}

	public ListeDeJoueurs getListeDeJoueurs() {
		return getListeJoueurs();
	}

	public void ajouterJoueur(Joueur player) {
		this.getListeJoueurs().ajouterJoueur(player);
	}
	
    public int nombreDeJoueurs() {
		return this.getListeJoueurs().getNombreDeConnecte();
	}
    
	public Joueur getJoueur(int id) {
		return this.getListeJoueurs().getJoueur(id);
	}
	
	public ListeDeEnnemis GetListeDeEnnemis() {
		return getListeEnnemis();
	}

	public ListeDeJoueurs getListeJoueurs() {
		return listeJoueurs;
	}

	public void setListeJoueurs(ListeDeJoueurs listeJoueurs) {
		this.listeJoueurs = listeJoueurs;
	}

	public ListeDeGraphiques getListeGraphiques() {
		return listeGraphiques;
	}

	public void setListeGraphiques(ListeDeGraphiques listeGraphiques) {
		this.listeGraphiques = listeGraphiques;
	}

	public ListeDeEnnemis getListeEnnemis() {
		return listeEnnemis;
	}

	public void setListeEnnemis(ListeDeEnnemis listeEnnemis) {
		this.listeEnnemis = listeEnnemis;
	}

	public ListeDeExplosions getListeExplosions() {
		return listeExplosions;
	}

	public void setListeExplosions(ListeDeExplosions listeExplosions) {
		this.listeExplosions = listeExplosions;
	}

	public ListeDeBonus getListeBonus() {
		return listeBonus;
	}

	public void setListeBonus(ListeDeBonus listeBonus) {
		this.listeBonus = listeBonus;
	}
}
