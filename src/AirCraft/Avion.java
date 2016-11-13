package AirCraft;

import Base.Graphique;

public class Avion extends Graphique{
	public static final double ANGLE = 2;
	
	private double vitesse;
	private double  vie;
	private int attaque;
	private int defense;
	public Avion(String Nom, int x, int y, int width,int height) {
		super(Nom,x,y,width,height);
		this.SetAngle(0);
		if (Nom.equals("MIG-51S"))
		{
			this.vie=10;
			this.vitesse=10;
			this.attaque=15;
			this.defense=30;
		}
		else if (Nom.equals("Su-55"))
		{
			this.vie=10;
			this.vitesse=10;
			this.attaque=15;
			this.defense=30;
		}
		else if (Nom.equals("Su-37K"))
		{
			this.vie=10;
			this.vitesse=10;
			this.attaque=15;
			this.defense=30;
		}
	}

	public void tournerGauche() {
		
		this.SetAngle(this.getAngle()+ANGLE);
	
	}
	public void SetVie(double vie)
	{
		this.vie=vie;
	}
	public void  SetAttaque (int attaque)
	{
		this.attaque=attaque;
	}
	public void SetDefense (int defense)
	{
		this.defense=defense;
	}
	public void SetVitesse(double vitesse)
	{
		this.vitesse=vitesse;
	}
	public double GetVie()
	{
		return this.vie;
	}
	public void tournerDroite() {
		this.SetAngle(this.getAngle()-ANGLE);
	}
	public void Blesser(Bullet balle)
	{
	
			this.SetVie(this.GetVie()-balle.GetDegat());
	}
	public void avancer() {
		this.SetX(this.GetX()+ (int)(vitesse*Math.cos(this.getAngle()*Math.PI/180)));
		this.SetY(this.GetY()+ (int)(vitesse*Math.sin(this.getAngle()*Math.PI/180)));
	}
	public void reculer() {
		this.SetX(this.GetX()- (int)(vitesse*Math.cos(this.getAngle()*Math.PI/180)));
		this.SetY(this.GetY()- (int)(vitesse*Math.sin(this.getAngle()*Math.PI/180)));
	}
}
