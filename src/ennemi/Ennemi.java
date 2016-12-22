package ennemi;

import avion.Avion;
import carte.ListeDeGraphiques;

public class Ennemi extends Avion {
	public Ennemi(String nom, int x, int y, int width, int height) {
		super(nom, x, y, width, height);

		if (nom.compareTo("ennemi1") == 0) {
			this.setVie(30);
			this.setAttaque(20);
			this.setDefense(10);
			this.setVitesse(10);
			this.setScore(50);
			this.setFrequenceActualisation(3000);

		} else if (nom.compareTo("ennemi2") == 0) {
			this.setVie(150);
			this.setAttaque(20);
			this.setDefense(10);
			this.setScore(70);
			this.setVitesse(20);
			this.setFrequenceActualisation(3000);

		} else if (nom.compareTo("ennemi3") == 0) {
			this.setVie(300);
			this.setAttaque(20);
			this.setDefense(10);
			this.setScore(30);
			this.setVitesse(10);
			this.setFrequenceActualisation(3000);

		} else if (nom.compareTo("ennemi4") == 0) {
			this.setVie(30);
			this.setAttaque(20);
			this.setScore(100);
			this.setDefense(10);
			this.setVitesse(10);
		}
	}

	public void tirer(ListeDeGraphiques listeDeGraphiques) {

		// Definition d'un comportement de tire pour lennemi1
		if (this.getNom().compareTo("ennemi1") == 0) {
			listeDeGraphiques.add(new BulletEnnemi(this.getX(), this.getY(), -180, this));
			listeDeGraphiques.add(new BulletEnnemi(this.getX(), this.getY(), -90, this));
		}
		// Definition d'un comportement de tire pour lennemi2
		else if (this.getNom().compareTo("ennemi2") == 0) {
			listeDeGraphiques.add(new BulletEnnemi(this.getX(), this.getY(), +90, this));
			listeDeGraphiques.add(new BulletEnnemi(this.getX(), this.getY(), -90, this));

		} else if (this.getNom().compareTo("ennemi3") == 0) {
			listeDeGraphiques.add(new BulletEnnemi(this.getX(), this.getY(), -70, this));
			listeDeGraphiques.add(new BulletEnnemi(this.getX(), this.getY(), +70, this));
		}
	}

	public void actualiser(long time, ListeDeGraphiques listeDeGraphiques) {
		this.tirer(listeDeGraphiques);
		this.setTimeLastActualisation(time);
	}
}
