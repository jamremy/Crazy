package airCraft;

import java.util.Iterator;

import bonus.Bonus;
import bonus.ListeDeBonus;
import carte.Graphique;
import carte.ListeDeGraphiques;

public class Avion extends Graphique {
	public static final double ANGLE = 2;
	public static final int LARGEUR = 1300;
	public static final int HAUTEUR = 700;
	
	private ListeDeBonus listeDeBonus = new ListeDeBonus();

	private double vitesse = 0.0;
	private double vie     = 0.0;
	private String bonus   = null;
	private int    attaque = 0;
	private int    defense = 0;

	public Avion(String nomAvion, int x, int y, int width, int height) {
		super(nomAvion, x, y, width, height);
		
		this.setAngle(0);

		System.out.println("Creation de l'avion : " + nomAvion);
		if ((nomAvion == null) || nomAvion.equals("Su-55") || nomAvion.equals("0")) { // L'avion par defaut
			this.vie     = 1000;
			this.vitesse = 10;
			this.attaque = 15;
			this.defense = 30;
			
		} else if (nomAvion.equals("MIG-51S") || nomAvion.equals("2")) {
			this.vie     = 1000;
			this.vitesse = 10;
			this.attaque = 15;
			this.defense = 30;
			
		} else if (nomAvion.equals("Su-37K")|| nomAvion.equals("1")) {
			this.vie     = 1000;
			this.vitesse = 10;
			this.attaque = 15;
			this.defense = 30;
		}
		
		System.out.print("Vie     : " + this.vie     + "\n" +
						 "Vitesse : " + this.vitesse + "\n" +
						 "Attaque : " + this.attaque + "\n" +
						 "Defense : " + this.defense + "\n");
	}

	public ListeDeBonus getMesBonus() {
		return listeDeBonus;
	}

	public void tournerGauche() {

		this.setAngle(this.getAngle() + Avion.ANGLE);

	}

	public void setVie(double vie) {
		this.vie = vie;
	}

	public void setAttaque(int attaque) {
		this.attaque = attaque;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}

	public double getVie() {
		return this.vie;
	}

	public void tournerDroite() {
		this.setAngle(this.getAngle() - Avion.ANGLE);
	}

	public void avancer() {
		int new_x = this.GetX() + (int) (vitesse * Math.cos(this.getAngle() * Math.PI / 180));
		int new_y = this.GetY() + (int) (vitesse * Math.sin(this.getAngle() * Math.PI / 180));

		System.out.println("New x : " + new_x + "\n" + "New y : " + new_y + "\n");
		if (new_x > 0 && new_x < Avion.HAUTEUR) {
			this.setX(new_x);
		}

		if (new_y > 0 && new_y < Avion.LARGEUR) {
			this.SetY(new_y);
		}
	}

	public void reculer() {
		int new_x = this.GetX() - (int) (vitesse * Math.cos(this.getAngle() * Math.PI / 180));
		int new_y = this.GetY() - (int) (vitesse * Math.sin(this.getAngle() * Math.PI / 180));

		if (new_x > 0 && new_x < Avion.HAUTEUR) {
			this.setX(new_x);
		}
		if (new_y > 0 && new_y < Avion.LARGEUR) {
			this.SetY(new_y);
		}
	}
	
	public void actualiser() {	
		for (Iterator<Bonus> iterator = listeDeBonus.iterator(); iterator.hasNext();) {
			Bonus bonusActuel = iterator.next();

			if (bonusActuel.GetDuree() == 0) {
				iterator.remove();

			} else {
				if (bonusActuel.GetNom().compareTo("Sante") == 0) {
					this.vie += 30;
					bonusActuel.use();
					
				} else if (bonusActuel.GetNom().compareTo("Bouclier") == 0) {
					bonusActuel.use();
				}
			}
		}
	}

	public void blesser(Bullet balle) {
		if (this.listeDeBonus.size() != 0) {
			if (this.listeDeBonus.get(0).GetNom().compareTo("Bouclier") != 0) {
				this.setVie(this.getVie() - balle.GetDegat());
			}
		} else {
			this.setVie(this.getVie() - balle.GetDegat());
		}

		if (balle.GetNom().compareTo("DeathBullet") == 0) {
			this.vie = 0;
		}
	}
	
	public void tirer(ListeDeGraphiques listeDeGraphiques) {
		if (this.listeDeBonus.size() != 0) {
			for (Bonus bonusActuel : listeDeBonus) {
				if (bonusActuel.GetNom().compareTo("Ballefoistrois") == 0) {
					listeDeGraphiques.add(new Bullet("Bullet", this.GetX() - (int) (50 * Math.sin(this.getAngle() * Math.PI / 180)),
							this.GetY() - (int) (50 * Math.cos(this.getAngle() * Math.PI / 180)), 21, 21,
							this.getAngle()));
					
					listeDeGraphiques.add(new Bullet("Bullet", this.GetX(), this.GetY(), 21, 21, this.getAngle()));

					listeDeGraphiques.add(new Bullet("Bullet", this.GetX() + (int) (50 * Math.sin(this.getAngle() * Math.PI / 180)),
							this.GetY() + (int) (50 * Math.cos(this.getAngle() * Math.PI / 180)), 21, 21,
							this.getAngle()));
					
					bonusActuel.use();
					
				} else if (bonusActuel.GetNom().compareTo("BallePerforante") == 0) {
					listeDeGraphiques.add(new Bullet("BallePerforante", this.GetX(), this.GetY(), 21, 21, this.getAngle()));
					bonusActuel.use();
				} else {
					listeDeGraphiques.add(new Bullet("Bullet", this.GetX(), this.GetY(), 21, 21, this.getAngle()));
				}
			}
			
		} else {
			listeDeGraphiques.add(new Bullet("Bullet", this.GetX(), this.GetY(), 21, 21, this.getAngle()));
		}
	}
}
