package airCraft;

import carte.Graphique;

public class Bullet extends Graphique {
	private int degats;
	private int vitesse;
	private Avion tireur;

	public Bullet(String nom, int x, int y, int width, int heigth, double angle, Avion tireur) {
		super(nom, x, y, width, heigth);
		this.vitesse = 10;
		this.degats = 5;
		this.tireur = tireur;
		this.setAngle(angle);
		this.setFrequenceActualisation(10);

		if (nom.compareTo("BallePerforante") == 0) {
			this.degats = 15;

		} else if (nom.compareTo("DeathBullet") == 0) {
			this.degats = 1000000;
		}
	}

	public Avion getTireur() {
		return this.tireur;
	}

	public void setDegats(int degats) {
		this.degats = degats;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	// Actualise la position de la balle selon le temps
	public void actualiser(long time) {
		// Si le temps est un multiple de deux alors la balle avance

		this.avancer();
		this.setTimeLastActualisation(time);
	}

	public void avancer() {
		this.setX(this.getX() - (int) (vitesse * Math.cos(this.getAngle() * Math.PI / 180)));
		this.setY(this.getY() - (int) (vitesse * Math.sin(this.getAngle() * Math.PI / 180)));
	}

	public int getDegat() {
		return this.degats;
	}
}
