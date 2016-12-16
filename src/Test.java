import com.sun.glass.ui.Application;

import carte.Carte;
import player.Joueur;
import player.ListeDeJoueurs;

public class Test {

	public static void funvar(final String... strings){
		for (String s : strings) {
			System.out.println(s);
		}
	}
	
	public static void main(String[] args) {
		Joueur player1 = new Joueur("player1", null);
		Joueur player2 = new Joueur("player2", null);
		Joueur player3 = new Joueur("player3", null);
		
		ListeDeJoueurs ldj = new ListeDeJoueurs();
		ldj.ajouterJoueur(player1);
		ldj.ajouterJoueur(player2);
		ldj.ajouterJoueur(player3);
		System.out.println(ldj.allPositions());
		
		//ldj.supprimerJoueur(2);
		//System.out.println(ldj.allPositions());
		
		Joueur nouveau = ldj.getJoueur(3);
		/*
		Carte carteActuel = new Carte("WebContent/maps/1.map");
		System.out.println("Map created");
		System.out.println(carteActuel.GetAllGraphiquesPosition());
		*/
		Class<?> clazz = Application.class;
		Package pckg = clazz.getPackage();
		System.out.println(pckg.getName());
		
		
		funvar("Hello", "World", "!");
	}
}
