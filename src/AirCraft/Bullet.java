package AirCraft;

import Base.Graphique;

public class Bullet extends Graphique {
	private int degats;
	private int vitesse;
	public Bullet(String nom, int x, int y, int width, int heigth,double angle) {
		super(nom, x, y, width, heigth);
		this.vitesse=10;
		this.degats=5;
		this.SetAngle(angle);
		
	}
	//Actualise la position de la balle selon le temps
	public void Actualiser(long time)
	{
		
	    // Si le temps est un multiple de deux alors la balle avance
		if (time%2==0)
		{
			this.Avancer();
		}
	}
	
	public void Avancer()
	{
		this.SetX(this.GetX()- (int)(vitesse*Math.cos(this.getAngle()*Math.PI/180)));
		this.SetY(this.GetY()- (int)(vitesse*Math.sin(this.getAngle()*Math.PI/180)));
	}

}
