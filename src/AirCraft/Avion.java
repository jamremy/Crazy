package AirCraft;

import Base.Graphique;

public class Avion extends Graphique{
	public static final double ANGLE = 5;
	private double angle;
	private double vitesse;
	private double  vie;
	private int attaque;
	private int defense;
	public Avion(String Nom, int x, int y, int width,int height) {
		super(Nom,x,y,width,height);
		this.angle = 0;
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
	public double getAngle() {
		return angle;
	}
	
	public void tournerGauche() {
		angle = angle + ANGLE;
	}

	public void tournerDroite() {
		angle = angle - ANGLE;
	}
	
	public void avancer() {
		this.SetX(this.GetX()+ (int)(vitesse*Math.cos(angle*Math.PI/180)));
		this.SetY(this.GetY()+ (int)(vitesse*Math.sin(angle*Math.PI/180)));
	}
	public void reculer() {
		this.SetX(this.GetX()- (int)(vitesse*Math.cos(angle*Math.PI/180)));
		this.SetY(this.GetY()- (int)(vitesse*Math.sin(angle*Math.PI/180)));
	}
}
