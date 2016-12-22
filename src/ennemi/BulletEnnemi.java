package ennemi;

import avion.Avion;
import avion.Bullet;

public class BulletEnnemi extends Bullet {

	public BulletEnnemi(int x, int y, double angle, Avion tireur) {
		super("BulletEnnemi", x, y, 40, 40, angle, tireur);

		this.setFrequenceActualisation(10);
		this.setVitesse(3);
		this.setDegats(5);
	}
}
