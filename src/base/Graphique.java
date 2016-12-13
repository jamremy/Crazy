package base;

public class Graphique {
	private static int increment = 0;

	private int x, y;
	private int height, width;
	private String nom;
	private int id;
	private double angle;
	private String type;
	private long TimeLastActualisation = 0; // Temps duquel la derniere
											// actualisation du graphique a ete
											// effectuee
	private long FrequenceActualisation = 100;// Temps devant s'ecouler avant la
												// prochaine actualisation du
												// graphique

	public Graphique(String nom, int x, int y, int width, int height) {
		this.x = (int) x;
		this.y = (int) y;
		this.nom = nom;
		this.width = width;
		this.height = height;
		this.id = ++increment;
	}

	public long GetTimeLastActualisation() {
		return this.TimeLastActualisation;
	}

	public void SetFrequenceActualisation(long time) {
		this.FrequenceActualisation = time;
	}

	public long GetFrequenceActualisation() {
		return this.FrequenceActualisation;
	}

	public void SetTimeLastActualisation(long time) {
		this.TimeLastActualisation = time;
	}

	public String GetType() {
		return this.type;
	}

	public void SetType(String type) {
		this.type = type;
	}

	public double getAngle() {
		return angle;
	}

	public void SetAngle(double angle) {
		this.angle = angle;
	}

	public String GetNom() {
		return nom;
	}

	public int GetId() {
		return id;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void SetY(int y) {
		this.y = y;
	}

	public int GetX() {
		return x;
	}

	public int GetY() {
		return y;
	}

	public boolean ColliDeRect(Graphique g) {

		if ((g.x >= x + width) // trop à droite
				|| (g.x + g.width <= x) || (g.y >= y + height) // trop à droite
				|| (g.y + g.height <= y))// trop à gauche

			return false;
		else
			return true;

	}

}
