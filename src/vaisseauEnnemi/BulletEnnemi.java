package vaisseauEnnemi;

import airCraft.Bullet;

public class BulletEnnemi extends Bullet {

	public BulletEnnemi(int x, int y, double angle) {
		super("BulletEnnemi", x, y, 40, 40, angle,null);
		// TODO Auto-generated constructor stub
		this.SetFrequenceActualisation(10);
		this.SetVitesse(3);
		this.SetDegats(5);
	}

}
