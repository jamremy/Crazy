package carte;

import java.io.BufferedReader;
import java.io.FileReader;

import org.json.JSONException;
import org.json.JSONObject;

import avion.Bullet;
import bonus.Bonus;
import bonus.ListeDeBonus;
import ennemi.BulletEnnemi;
import ennemi.Ennemi;
import ennemi.ListeDeEnnemis;
import explosion.ListeDeExplosions;
import player.Joueur;
import player.ListeDeJoueurs;

public class Carte {
	private ListeDeJoueurs listeJoueurs = new ListeDeJoueurs();
	private ListeDeGraphiques listeGraphiques = new ListeDeGraphiques();
	private ListeDeEnnemis listeEnnemis = new ListeDeEnnemis();
	private ListeDeExplosions listeExplosions = new ListeDeExplosions();
	private ListeDeBonus listeBonus = new ListeDeBonus();
	
	long TimeLastActualisation = 0;
	
	private int nbEnnemiPossible = 3;
	private int nbBonusPossible = 3;
	
	private float probabiliteApparitionBonus = 1f / 500f;
	private float probabiliteEnnemie = 1f / 15f;

	char BufferMap[][] = new char[1000][];

	int frequenceActualisation = 50;

	public Carte(String cheminCarte) {
		int i = 0;

		// Chargement du contenu de la carte
		try {
			BufferedReader bufferReader = new BufferedReader(new FileReader(cheminCarte));
			String ligne = null;

			while ((ligne = bufferReader.readLine()) != null) {
				if (i < 1000) {
					BufferMap[i] = ligne.toCharArray();
					i++;
				}
			}

			bufferReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Creation des graphiques associes a la carte
		for (i = 0; i < BufferMap.length; i++) {
			for (int j = 0; j < BufferMap[i].length; j++) {
				if (BufferMap[i][j] != 'A') {
					if (BufferMap[i][j] == 'B') {
						this.getListeEnnemis().add(new Ennemi("ennemi1", i * -80, j * 80, 100, 100));
						
					} else if (BufferMap[i][j] == 'S') {
						this.getListeBonus().add(new Bonus("Sante", i * -80, j * 80, 100, 100));
						
					} else if (BufferMap[i][j] == 'T') {
						this.getListeBonus().add(new Bonus("BallePerforante", i * -80, j * 80, 100, 100));
						
					} else if (BufferMap[i][j] == 'M') {
						this.getListeBonus().add(new Bonus("Missile", i * -80, j * 80, 100, 100));
						
					} else if (BufferMap[i][j] == '3') {
						this.getListeBonus().add(new Bonus("Ballefoistrois", i * -80, j * 80, 100, 100));
						
					} else if (BufferMap[i][j] == 'O') {
						this.getListeBonus().add(new Bonus("Bouclier", i * -80, j * 80, 100, 100));
					}
				}
			}
		}
	}

	// Actualise la liste de graphique prśent sur la map
	public void actualiser(long time) {
		if (Math.abs(time - TimeLastActualisation) > this.frequenceActualisation) {
			double alea = Math.random();
			
			if (alea < probabiliteApparitionBonus) {
				int bonus = (int) (Math.random() * nbBonusPossible);
				
				if (bonus == 0) {
					this.getListeBonus().add(new Bonus("Sante", -100, (int) (Math.random() * 1000), 100, 100));
					
				} else if (bonus == 1) {
					this.getListeBonus().add(new Bonus("Bouclier", -100, (int) (Math.random() * 1000), 100, 100));

				} else if (bonus == 2) {
					this.getListeBonus().add(new Bonus("Ballefoistrois", -100, (int) (Math.random() * 1000), 100, 100));

				}
			} else if (alea < probabiliteEnnemie) {
				int bonus = (int) (Math.random() * nbEnnemiPossible);
				if (bonus == 0) {
					this.getListeEnnemis().add(new Ennemi("ennemi1", -100, (int) (Math.random() * 1000), 100, 100));
					
				} else if (bonus == 1) {
					this.getListeEnnemis().add(new Ennemi("ennemi2", -100, (int) (Math.random() * 1000), 100, 100));

				} else if (bonus == 2) {
					this.getListeEnnemis().add(new Ennemi("ennemi3", -100, (int) (Math.random() * 1000), 100, 100));

				}
			}
			getListeEnnemis().move_all(5, 0);
			getListeBonus().move_all(5, 0);
			TimeLastActualisation = time;
		}

		getListeGraphiques().actualiser(time);
		getListeEnnemis().actualiser(time, getListeGraphiques(), getListeExplosions());
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

	public ListeDeGraphiques getListeDeGraphiques() {
		return getListeGraphiques();
	}

	// Detecte et traite les collision entre les different objet present sur la carte
	public void detectCollision() {
		for (Bonus bonusActuel : getListeBonus()) {
			for (Joueur j : getListeJoueurs().getListeJoueur()) {
				// en cas de collision entre un avion et un bonus on affecte ce bonus a la liste des bonus de l'avion
				if (bonusActuel.detectCollision(j.getAvion()) == true) {
					j.getAvion().getBonus().add(bonusActuel);
					bonusActuel.setDejaUtilise(true);
				}
			}
		}

		for (Graphique graphiqueActuel : getListeGraphiques()) {
			for (Joueur joueursActuel : getListeJoueurs().getListeJoueur()) {
				// si il y a une collision entre un avion et un graphique present sur la map
				if (graphiqueActuel.detectCollision(joueursActuel.getAvion()) == true) {
					// sil sagit d'une balle ennemis
					if (graphiqueActuel instanceof BulletEnnemi) {
						// blesser l'avion
						joueursActuel.getAvion().blesser((BulletEnnemi) graphiqueActuel);
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
				if (ennemiActuel.detectCollision(j.getAvion()) == true) {
					ennemiActuel.blesser(new Bullet("DeathBullet", 0, 0, 100, 100, 20, j.getAvion()));
					j.getAvion().blesser(new Bullet("BallePerforante", 0, 0, 100, 100, 20, ennemiActuel));
				}
			}
		}

		for (Graphique graphiqueActuel : getListeGraphiques()) {
			for (Ennemi ennemiActuel : getListeEnnemis()) {
				// si il y a une collision entre un ennemi et un graphique present sur la map
				if (graphiqueActuel.detectCollision(ennemiActuel) == true || ennemiActuel.detectCollision(graphiqueActuel) == true) {
					if (graphiqueActuel instanceof BulletEnnemi) {

					}
					// sil sagit d'une balle
					else if (graphiqueActuel instanceof Bullet) {
						// blesser l'ennemis
						ennemiActuel.blesser((Bullet) graphiqueActuel);
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

	public ListeDeEnnemis getListeDeEnnemis() {
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
