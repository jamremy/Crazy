package bonus;

import carte.Graphique;

public class Bonus extends Graphique {
	private int duree;
	private boolean dejaUtilise = false;
	
	public Bonus(String nom, int x, int y, int width, int height) {
		super(nom, x, y, width, height);
		
		if (nom.compareTo("Sante") == 0) {
				this.duree = 1;
				
		} else if (nom.compareTo("BallePerforante") == 0) {
			this.duree = 20;
			
		} else if (nom.compareTo("Ballefoistrois") == 0) {
			this.duree = 300;
			
		} else if (nom.compareTo("Bouclier") == 0) {
				duree=3000;
				
		} else if (nom.compareTo("Missile") == 0) {
			this.duree = 5;
		}
	}

	public int getDuree() {
		return this.duree;
	}
	
	public void setDejaUtilise(boolean etat) {
		this.dejaUtilise = etat;
	}
	
	public boolean getDejaUtilise() {
		return this.dejaUtilise;
	}
	
	public void consommer() {
		this.duree -= 1;
	}
	
	

	
	

	

}
