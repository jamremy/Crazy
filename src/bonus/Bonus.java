package bonus;

import base.Graphique;

public class Bonus extends Graphique {
	private int Duree;
	private boolean DejaUtilise = false;
	
	public Bonus(String nom, int x, int y, int width, int height) {
		super(nom, x, y, width, height);
		
		if (nom.compareTo("Sante") == 0) {
				Duree = 1;
		} else if (nom.compareTo("BallePerforante") == 0) {
				Duree = 20;
		} else if (nom.compareTo("Ballefoistrois") == 0) {
				Duree = 300;
		} else if (nom.compareTo("Bouclier") == 0) {
				Duree=3000;
		} else if (nom.compareTo("Missile") == 0) {
				Duree = 5;
		}
	}

	public int GetDuree() {
		return Duree;
	}
	
	public void Set_DejaUtilise(boolean etat) {
		this.DejaUtilise = etat;
	}
	
	public boolean Get_DejaUtilise() {
		return this.DejaUtilise;
	}
	
	public void use() {
		Duree -= 1;
	}
	
	

	
	

	

}
