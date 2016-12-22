package avion;

import carte.Graphique;

public class Bullet extends Graphique {
	private int degats;
	private Avion tireur;

	public Bullet(String nom, int x, int y, int width, int heigth, double angle, Avion tireur) {
		super(nom, x, y, width, heigth);
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

	// Actualise la position de la balle selon le temps
	public void actualiser(long time) {
		// Si le temps est un multiple de deux alors la balle avance
		this.avancer();
		this.setTimeLastActualisation(time);
	}

	public void avancer() {
		try {
			// Si le parseInt foctionnne c'est que c'est un joueur.
			@SuppressWarnings("unused")
			int playerId = Integer.parseInt(tireur.getNom());
			
			this.setX(this.getX() - (int) (tireur.getVitesseTire() * Math.cos(this.getAngle() * Math.PI / 180)));
			this.setY(this.getY() - (int) (tireur.getVitesseTire() * Math.sin(this.getAngle() * Math.PI / 180)));
			
		}
		catch (NumberFormatException e){
			this.setX((int) (this.getX() + tireur.getVitesseTire()) );
		}
	}

	public int getDegat() {
		return this.degats;
	}
	
	public Avion getTireur() {
		return this.tireur;
	}

	public void setDegats(int degats) {
		this.degats = degats;
	}
}
