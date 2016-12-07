package base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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
		int x, y;
		String chaine = "";
		
		// Chargement du contenu de la carte
		try {		
			//InputStream       ips   = new FileInputStream(cheminCarte);			
			//InputStreamReader ipsr  = new InputStreamReader(ips);
			BufferedReader    br    = new BufferedReader(new FileReader(cheminCarte));
			String            ligne = null;
			
			while ((ligne = br.readLine()) != null) {
				chaine += ligne + "\n";

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
						this.listeEnnemis.add(new Ennemi("ennemi1", i * -80, j * 80, 100, 100));
					}
					else if (BufferMap[i][j] == 'S') {
						this.listeBonus.add(new Bonus("Sante", i * -80, j * 80, 100, 100));
					}
					else if (BufferMap[i][j] == 'T') {
						this.listeBonus.add(new Bonus("BallePerforante", i * -80, j * 80, 100, 100));
					}
					else if (BufferMap[i][j] == 'M') {
						this.listeBonus.add(new Bonus("Missile", i * -80, j * 80, 100, 100));
					}
					else if (BufferMap[i][j] == '3') {
						this.listeBonus.add(new Bonus("Ballefoistrois", i * -80, j * 80, 100, 100));
					}
					else if (BufferMap[i][j] == 'O') {
						this.listeBonus.add(new Bonus("Bouclier", i * -80, j * 80, 100, 100));
					}
				}
			}
		}
	}

	// Actualise la liste de graphique prśent sur la map
	public void Actualiser(long time) {
		if (Math.abs(time - TimeLastActualisation) > 50) {
			listeEnnemis.move_all(5, 0);
			listeBonus.move_all(5, 0);
			TimeLastActualisation = time;
		}
		
		System.out.println(time - TimeLastActualisation);

		listeGraphiques.Actualiser(time);
		listeEnnemis.Actualiser(time, listeGraphiques, listeExplosions);
		listeJoueurs.Actualiser(time);
		listeBonus.Actualiser(time);
		listeExplosions.Actualiser(time);

	}

	public JSONObject GetAllGraphiquesPosition() {
		JSONObject positions = new JSONObject();
		
		try {
			positions.accumulate("Carte", this.listeJoueurs.allPositions());
			positions.accumulate("Carte", this.listeGraphiques.allPositions());
			positions.accumulate("Carte", this.listeEnnemis.allPositions());
			positions.accumulate("Carte", this.listeExplosions.allPositions());
			positions.accumulate("Carte", this.listeBonus.allPositions());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return positions;
	}

	public ListeDeGraphiques GetListeDeGraphiques() {
		return listeGraphiques;
	}

	// Detecte et traite les collision entre les different objet present sur la carte
	public void DetectCollision() {
		for (Bonus bonusActuel : listeBonus) {
			for (Joueur j : listeJoueurs.GetListeJoueur()) {
				// en cas de collision entre un avion et un bonus on affecte ce bonus a la liste des bonus de l'avion
				if (bonusActuel.ColliDeRect(j.GetAvion()) == true) {
					j.GetAvion().GetMesBonus().add(bonusActuel);
					bonusActuel.Set_DejaUtilise(true);
				}
			}
		}

		for (Graphique graphiqueActuel : listeGraphiques) {
			for (Joueur joueursActuel : listeJoueurs.GetListeJoueur()) {
				// si il y a une collision entre un avion et un graphique present sur la map
				if (graphiqueActuel.ColliDeRect(joueursActuel.GetAvion()) == true) {
					// sil sagit d'une balle ennemis
					if (graphiqueActuel instanceof BulletEnnemi) {
						// blesser l'avion
						joueursActuel.GetAvion().Blesser((BulletEnnemi) graphiqueActuel);
						// Detruire le balle
						graphiqueActuel.SetX(-500);
					}
				}
			}
		}
		
		for (Ennemi ennemiActuel : listeEnnemis) {
			for (Joueur j : listeJoueurs.GetListeJoueur()) {
				// en cas de collision entre un ennemi et un avion on detruit lennemi et on retire des point de vie a
				// l'avion
				if (ennemiActuel.ColliDeRect(j.GetAvion()) == true) {
					ennemiActuel.Blesser(new Bullet("DeathBullet", 0, 0, 100, 100, 20));
					j.GetAvion().Blesser(new Bullet("BallePerforante", 0, 0, 100, 100, 20));
				}
			}
		}

		for (Graphique graphiqueActuel : listeGraphiques) {
			for (Ennemi ennemiActuel : listeEnnemis) {
				// si il y a une collision entre un ennemi et un graphique present sur la map
				if (graphiqueActuel.ColliDeRect(ennemiActuel) == true || ennemiActuel.ColliDeRect(graphiqueActuel) == true) {
					if (graphiqueActuel instanceof BulletEnnemi) {

					}
					// sil sagit d'une balle
					else if (graphiqueActuel instanceof Bullet) {
						// blesser l'ennemis
						ennemiActuel.Blesser((Bullet) graphiqueActuel);
						// Detruire le balle
						graphiqueActuel.SetX(-500);

					}
				}
			}
		}
	}

	public ListeDeJoueurs GetListeDeJoueurs() {
		return listeJoueurs;
	}

	public ListeDeEnnemis GetListeDeEnnemis() {
		return listeEnnemis;
	}
}
