package airCraft;

import java.util.Iterator;

import base.Graphique;
import base.ListeDeGraphiques;
import bonus.Bonus;
import bonus.ListeDeBonus;

public class Avion extends Graphique {
	public static final double ANGLE = 2;

	private ListeDeBonus listeDeBonus = new ListeDeBonus();

	private double vitesse = 0.0;
	private double vie     = 0.0;
	private String bonus   = null;
	private int    attaque = 0;
	private int    defense = 0;

	public Avion(String nomAvion, int x, int y, int width, int height) {
		super(nomAvion, x, y, width, height);
		
		this.SetAngle(0);

		if ((nomAvion == null) || nomAvion.equals("MIG-51S")) { // L'avion par defaut
			this.vie     = 1000;
			this.vitesse = 10;
			this.attaque = 15;
			this.defense = 30;
			
		} else if (nomAvion.equals("Su-55")) {
			this.vie     = 1000;
			this.vitesse = 10;
			this.attaque = 15;
			this.defense = 30;
			
		} else if (nomAvion.equals("Su-37K")) {
			this.vie     = 1000;
			this.vitesse = 10;
			this.attaque = 15;
			this.defense = 30;
		}
	}

	public ListeDeBonus GetMesBonus() {
		return listeDeBonus;
	}

	public void tournerGauche() {

		this.SetAngle(this.getAngle() + ANGLE);

	}

	public void SetVie(double vie) {
		this.vie = vie;
	}

	public void SetAttaque(int attaque) {
		this.attaque = attaque;
	}

	public void SetDefense(int defense) {
		this.defense = defense;
	}

	public void SetVitesse(double vitesse) {
		this.vitesse = vitesse;
	}

	public double getVie() {
		return this.vie;
	}

	public void tournerDroite() {
		this.SetAngle(this.getAngle() - ANGLE);
	}

	public void Blesser(Bullet balle) {
		if (this.listeDeBonus.size() != 0) {
			if (this.listeDeBonus.get(0).GetNom().compareTo("Bouclier") != 0) {
				this.SetVie(this.getVie() - balle.GetDegat());
			}
		} else {
			this.SetVie(this.getVie() - balle.GetDegat());
		}

		if (balle.GetNom().compareTo("DeathBullet") == 0) {
			this.vie = 0;
		}
	}

	public void avancer() {
		int new_x = this.GetX() + (int) (vitesse * Math.cos(this.getAngle() * Math.PI / 180));
		int new_y = this.GetY() + (int) (vitesse * Math.sin(this.getAngle() * Math.PI / 180));

		if (new_x > 0 && new_x < 600) {
			this.setX(new_x);
		}

		if (new_y > 0 && new_y < 700) {
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

	public void reculer() {
		int new_x = this.GetX() - (int) (vitesse * Math.cos(this.getAngle() * Math.PI / 180));
		int new_y = this.GetY() - (int) (vitesse * Math.sin(this.getAngle() * Math.PI / 180));

		if (new_x > 0 && new_x < 600) {
			this.setX(new_x);
		}
		if (new_y > 0 && new_y < 700) {
			this.SetY(new_y);
		}
	}
}
