package AirCraft;

import Base.Graphique;

public class Bullet extends Graphique {
	private int degats;
	private int vitesse;
	public Bullet(String nom, int x, int y, int width, int heigth) {
		super(nom, x, y, width, heigth);
		
	}
	
	public void Actualiser(long time)
	{
		
	}
	
	public void Avancer()
	{
		this.SetX(this.GetX()+ (int)(vitesse*Math.cos(this.getAngle()*Math.PI/180)));
		this.SetY(this.GetY()+ (int)(vitesse*Math.sin(this.getAngle()*Math.PI/180)));
	}

}
