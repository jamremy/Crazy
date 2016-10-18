import java.lang.Math;

public class Voiture {
	public static final double ANGLE = 5;
	public static final double ACCELERATION = 3;
	public static final double DECELERATION = 1;
	
	private double angle;
	private double x;
	private double y;
	private double acceleration;
	private double deceleration;
	private double vitesse;
	
	public Voiture() {
		this.x = 0;
		this.y = 0;
		this.angle = 0;
		this.acceleration = 0;
		this.deceleration = 0;
		this.vitesse = 0;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
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
		x = x + vitesse*Math.cos(angle*Math.PI/180);
		y = y + vitesse*Math.sin(angle*Math.PI/180);
		vitesse += ACCELERATION;
	}
	
	public void reculer() {
		x = x + vitesse*Math.cos(angle*Math.PI/180);
		y = y + vitesse*Math.sin(angle*Math.PI/180);
		vitesse -= ACCELERATION;
	}
	
	public void decelerer() {
		if(vitesse != 0) {
			if(vitesse > 0) {
				vitesse -= DECELERATION;
			}
			else {
				vitesse += DECELERATION;
			}
		}
	}

}
