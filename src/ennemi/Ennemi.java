package ennemi;

import avion.Avion;
import carte.ListeDeGraphiques;

public class Ennemi extends Avion {
	public Ennemi(String nom, int x, int y, int width, int height) {
		super(nom, x, y, width, height);

		if (nom.compareTo("ennemi1") == 0) {
			this.setVie(150);
			this.setAttaque(20);
			this.setDefense(10);
			this.setVitesseDeplacement(10);
			super.setVitesseTire(7);
			this.setScore(70);

		} else if (nom.compareTo("ennemi2") == 0) {
			this.setVie(50);
			this.setAttaque(20);
			this.setDefense(10);
			this.setVitesseDeplacement(10);
			super.setVitesseTire(7);
			this.setScore(70);

		} else if (nom.compareTo("ennemi3") == 0) {
			this.setVie(50);
			this.setAttaque(20);
			this.setDefense(10);
			this.setVitesseDeplacement(10);
			super.setVitesseTire(7);
			this.setScore(70);

		} else if (nom.compareTo("ennemi4") == 0) {
			this.setVie(150);
			this.setAttaque(20);
			this.setDefense(10);
			this.setVitesseDeplacement(10);
			super.setVitesseTire(7);
			this.setScore(70);
		}
		
		this.setFrequenceActualisation(3000);
	}

	public void actualiser(long time, ListeDeGraphiques listeDeGraphiques) {
		this.tirer(listeDeGraphiques);
		this.setTimeLastActualisation(time);
	}
}
