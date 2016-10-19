import player.Joueur;
import player.ListeDeJoueurs;

public class Test {

	public static void main(String[] args) {
		Joueur player1 = new Joueur("player1");
		Joueur player2 = new Joueur("player2");
		Joueur player3 = new Joueur("player3");
		
		ListeDeJoueurs ldj = new ListeDeJoueurs();
		ldj.addJoueur(player1);
		ldj.addJoueur(player2);
		ldj.addJoueur(player3);
		System.out.println(ldj.allPositions());
		
		ldj.deleteJoueur(2);
		System.out.println(ldj.allPositions());
		
		Joueur nouveau = ldj.searchJoueur(3);
		
	}
}
