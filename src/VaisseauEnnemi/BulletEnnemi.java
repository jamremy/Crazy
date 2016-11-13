package VaisseauEnnemi;

import AirCraft.Bullet;

public class BulletEnnemi extends Bullet {

	public BulletEnnemi(int x, int y, double angle) {
		super("BulletEnnemi", x, y, 40, 40, angle);
		// TODO Auto-generated constructor stub
		this.SetFrequenceActualisation(10);
		this.SetVitesse(3);
		this.SetDegats(5);
	}

}
