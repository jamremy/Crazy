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
		this.SetFrequenceActualisation(10);
		if (nom.compareTo("BallePerforante")==0)
		{
			this.degats=15;
		}
		else if (nom.compareTo("DeathBullet")==0)
		{
			this.degats=1000000;
		}
	}
	
	public  void SetDegats(int degats)
	{
		this.degats=degats;
	}
	
	public void SetVitesse(int vitesse)
	{
		this.vitesse=vitesse;
	}
	//Actualise la position de la balle selon le temps
	public void Actualiser(long time)
	{
	    // Si le temps est un multiple de deux alors la balle avance
		
			this.Avancer();
			this.SetTimeLastActualisation(time);
		
	}
	
	public void Avancer()
	{
		this.SetX(this.GetX()- (int)(vitesse*Math.cos(this.getAngle()*Math.PI/180)));
		this.SetY(this.GetY()- (int)(vitesse*Math.sin(this.getAngle()*Math.PI/180)));
	}
	public int GetDegat() 
	{
		return this.degats;	
	}

}
