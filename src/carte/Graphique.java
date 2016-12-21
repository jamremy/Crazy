package carte;

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

	public long getTimeLastActualisation() {
		return this.TimeLastActualisation;
	}

	public void setFrequenceActualisation(long time) {
		this.FrequenceActualisation = time;
	}

	public long getFrequenceActualisation() {
		return this.FrequenceActualisation;
	}

	public void setTimeLastActualisation(long time) {
		this.TimeLastActualisation = time;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public String getNom() {
		return nom;
	}

	public int getId() {
		return id;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean detectCollision(Graphique g) {

		if ((g.x >= x + width) // trop à droite
				|| (g.x + g.width <= x) || (g.y >= y + height) // trop à droite
				|| (g.y + g.height <= y))// trop à gauche

			return false;
		else
			return true;

	}

}
