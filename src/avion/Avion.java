package avion;

import java.util.Iterator;

import carte.Graphique;
import carte.ListeDeGraphiques;
import ennemi.Ennemi;
import bonus.Bonus;
import bonus.ListeDeBonus;

public class Avion extends Graphique {
	public static final int LARGEUR = 1300;
	public static final int HAUTEUR = 700;
	public static final int MARGIN = 75;

	private ListeDeBonus listeDeBonus = new ListeDeBonus();

	private double vitesseDeplacement = 0.0;
	private double vitesseRotation = 0.0;
	private double vitesseTire = 0.0;

	private double vie = 0.0;
	
	private String vraiNom = null;
	
	private int score = 0;

	/* Variables non utilise */
	@SuppressWarnings("unused")
	private int attaque = 0;
	@SuppressWarnings("unused")
	private int defense = 0;
	//private String bonus = null;
	
	private int imageWidth = 56;
	@SuppressWarnings("unused")
	private int imageHeight = 88;//Hauteur moyenne des (images) avions

	public Avion(String nomAvion, int x, int y, int width, int height) {
		super(nomAvion, x, y, width, height);

		this.setAngle(0);

		if ((nomAvion == null) || nomAvion.equals("Su-55") || nomAvion.equals("0")) { // L'avion par defaut
			this.vie = 1000;
			this.vitesseDeplacement = 30;
			this.vitesseRotation = 10;
			this.vitesseTire = 15;
			this.attaque = 40;
			this.defense = 40;
			this.vraiNom = "Su-55";

		} else if (nomAvion.equals("MIG-51S") || nomAvion.equals("2")) {
			this.vie = 1000;
			this.vitesseDeplacement = 40;
			this.vitesseRotation = 15;
			this.vitesseTire = 15;
			this.attaque = 20;
			this.defense = 30;
			this.vraiNom = "MIG-51S";

		} else if (nomAvion.equals("Su-35K") || nomAvion.equals("1")) {
			this.vie = 1700;
			this.vitesseDeplacement = 30;
			this.vitesseRotation = 10;
			this.vitesseTire = 15;
			this.attaque = 20;
			this.defense = 30;
			this.vraiNom = "Su-35K";
		}
	}
	
	public void blesser(Bullet balle) {
		boolean boolBouclier = false;

		if (this.listeDeBonus.size() != 0) {
			for (int i = 0; i < this.listeDeBonus.size(); i++) {
				if (this.listeDeBonus.get(i).getNom().equals("Bouclier")) {
					boolBouclier = true;
				}
			}

			if (boolBouclier == false) {
				this.setVie(this.getVie() - balle.getDegat());
			}

			if (this.getVie() <= 0) {
				if (this instanceof Ennemi) {
					balle.getTireur().setScore(balle.getTireur().getScore() + this.getScore());
				}
			}
		} else {
			this.setVie(this.getVie() - balle.getDegat());
			if (this.getVie() <= 0) {
				if (this instanceof Ennemi) {
					balle.getTireur().setScore(balle.getTireur().getScore() + this.getScore());
				}
			}
		}

		if (balle.getNom().compareTo("DeathBullet") == 0) {
			this.setVie(0);
			if (this instanceof Ennemi) {
				balle.getTireur().setScore(balle.getTireur().getScore() + this.getScore());
			}
		}
	}

	public void avancer() {
		int new_x = this.getX() + (int) (this.vitesseDeplacement * Math.cos(Math.toRadians(this.getAngle())));
		int new_y = this.getY() + (int) (this.vitesseDeplacement * Math.sin(Math.toRadians(this.getAngle())));

		if (new_x > 0 && new_x < Avion.HAUTEUR - Avion.MARGIN) {
			this.setX(new_x);
		}

		if (new_y > 0 && new_y < Avion.LARGEUR - Avion.MARGIN) {
			this.setY(new_y);
		}
	}

	public void reculer() {
		int new_x = this.getX() - (int) (this.vitesseDeplacement * Math.cos(Math.toRadians(this.getAngle())));
		int new_y = this.getY() - (int) (this.vitesseDeplacement * Math.sin(Math.toRadians(this.getAngle())));

		if (new_x > 0 && new_x < Avion.HAUTEUR - Avion.MARGIN) {
			this.setX(new_x);
		}

		if (new_y > 0 && new_y < Avion.LARGEUR - Avion.MARGIN) {
			this.setY(new_y);
		}
	}

	public void actualiser() {
		for (Iterator<Bonus> iterator = listeDeBonus.iterator(); iterator.hasNext();) {
			Bonus bonusActuel = iterator.next();

			if (bonusActuel.getDuree() == 0) {
				iterator.remove();

			} else {
				if (bonusActuel.getNom().compareTo("Sante") == 0) {
					this.vie += 30;
					bonusActuel.consommer();

				} else if (bonusActuel.getNom().compareTo("Bouclier") == 0) {
					bonusActuel.consommer();
				}
			}
		}
	}

	public void tirer(ListeDeGraphiques listeDeGraphiques) {
		int bulletX = (int) (this.getX() - Math.toRadians(this.getAngle()));
		int bulletY = (int) ((this.getY() + this.imageWidth / 2 + 1) + Math.toRadians(this.getAngle()));

		if (this.listeDeBonus.size() != 0) {
			for (Bonus bonusActuel : listeDeBonus) {
				if (bonusActuel.getNom().compareTo("Ballefoistrois") == 0) {
					listeDeGraphiques.add(
							new Bullet("Bullet", this.getX() - (int) (50 * Math.sin(Math.toRadians(this.getAngle()))),
									this.getY() - (int) (50 * Math.cos(Math.toRadians(this.getAngle()))), 21, 21,
									this.getAngle(), this));

					listeDeGraphiques
							.add(new Bullet("Bullet", this.getX(), this.getY(), 21, 21, this.getAngle(), this));

					listeDeGraphiques.add(
							new Bullet("Bullet", this.getX() + (int) (50 * Math.sin(Math.toRadians(this.getAngle()))),
									this.getY() + (int) (50 * Math.cos(Math.toRadians(this.getAngle()))), 21, 21,
									this.getAngle(), this));

					bonusActuel.consommer();
					
				} else if (bonusActuel.getNom().compareTo("BallePerforante") == 0) {
					listeDeGraphiques.add(new Bullet("Bullet", bulletX, bulletY, 21, 21, this.getAngle(), this));
					bonusActuel.consommer();
					
				} else {
					listeDeGraphiques
							.add(new Bullet("Bullet", bulletX, bulletY, 21, 21, this.getAngle(), this));
				}
			}
		} else {
			listeDeGraphiques.add(new Bullet("Bullet", bulletX, bulletY, 21, 21, this.getAngle(), this));
		}
	}

	public ListeDeBonus getBonus() {
		return listeDeBonus;
	}

	public void tournerGauche() {
		this.setAngle(this.getAngle() + this.vitesseRotation);
	}
	
	public void tournerDroite() {
		this.setAngle(this.getAngle() - this.vitesseRotation);
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

	public void setVitesseDeplacement(double vitesse) {
		this.vitesseDeplacement = vitesse;
	}

	public double getVitesseDeplacement() {
		return this.vitesseDeplacement;
	}
	
	public double getVie() {
		return this.vie;
	}

	public String getVraiNom() {
		return vraiNom;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return this.score;
	}
	
	public double getVitesseTire() {
		return vitesseTire;
	}

	public void setVitesseTire(double vitesseTire) {
		this.vitesseTire = vitesseTire;
	}
}
