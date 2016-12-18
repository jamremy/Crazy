package airCraft;

import java.util.Iterator;

import vaisseauEnnemi.Ennemi;
import carte.Graphique;
import carte.ListeDeGraphiques;
import bonus.Bonus;
import bonus.ListeDeBonus;

public class Avion extends Graphique {
	public static final double ANGLE = 2;

	private ListeDeBonus listeDeBonus = new ListeDeBonus();
	private int LONGUEUR_ECRAN=1300;
	private int HAUTEUR_ECRAN=700;
	private double vitesse = 0.0;
	private double vie     = 0.0;
	private String bonus   = null;
	private int    attaque = 0;
	private int    defense = 0;
	private int score=0;

	public Avion(String nomAvion, int x, int y, int width, int height) {
		super(nomAvion, x, y, width, height);
		
		this.SetAngle(0);

		if ((nomAvion == null) || nomAvion.equals("MIG-51S")) { // L'avion par defaut
			this.SetNom("MIG-51S");
			this.vie     = 1000;
			this.vitesse = 10;
			this.attaque = 15;
			this.defense = 30;
			
		} else if (nomAvion.equals("Su-55")) {
			this.vie     = 1000;
			this.vitesse = 10;
			this.attaque = 15;
			this.defense = 30;
			
		} else if (nomAvion.equals("Su-37K")) {
			this.vie     = 1000;
			this.vitesse = 10;
			this.attaque = 15;
			this.defense = 30;
		}
	}

	public ListeDeBonus GetMesBonus() {
		return listeDeBonus;
	}

	public void tournerGauche() {

		this.SetAngle(this.getAngle() + ANGLE);

	}

	public void SetVie(double vie) {
		this.vie = vie;
	}

	public void SetAttaque(int attaque) {
		this.attaque = attaque;
	}

	public void SetDefense(int defense) {
		this.defense = defense;
	}

	public void SetVitesse(double vitesse) {
		this.vitesse = vitesse;
	}

	public double getVie() {
		return this.vie;
	}

	public void tournerDroite() {
		this.SetAngle(this.getAngle() - ANGLE);
	}

	public void Blesser(Bullet balle) {
		boolean bool_bouclier=false;
		if (this.listeDeBonus.size() != 0) {
			
			for (int i=0;i<this.listeDeBonus.size();i++)
			{
				if (this.listeDeBonus.get(i).GetNom().equals("Bouclier"))
				{
					bool_bouclier=true;
				}
			}
			
			if (bool_bouclier==false) {
				this.SetVie(this.getVie() - balle.GetDegat());
			}
			
			if (this.getVie()<=0)
			{
			if (this instanceof Ennemi )
			{
				balle.GetTireur().SetScore(balle.GetTireur().GetScore()+this.GetScore());
			}
			}
		} else {
			this.SetVie(this.getVie() - balle.GetDegat());
			if (this.getVie()<=0)
			{
			if (this instanceof Ennemi )
			{
				balle.GetTireur().SetScore(balle.GetTireur().GetScore()+this.GetScore());
			}
			}
		}

		if (balle.GetNom().compareTo("DeathBullet") == 0) {
			this.vie = 0;
			if (this instanceof Ennemi )
			{
				balle.GetTireur().SetScore(balle.GetTireur().GetScore()+this.GetScore());
			}
		}
		
	}
	public void SetScore(int score)
	{
		this.score=score;
	}
	public int GetScore()
	{
		return this.score;
	}
	public void avancer() {
		int new_x = this.GetX() + (int) (vitesse * Math.cos(this.getAngle() * Math.PI / 180));
		int new_y = this.GetY() + (int) (vitesse * Math.sin(this.getAngle() * Math.PI / 180));

		if (new_x > 0 && new_x < HAUTEUR_ECRAN-100) {
			this.setX(new_x);
		}

		if (new_y > 0 && new_y <  LONGUEUR_ECRAN-100) {
			this.SetY(new_y);
		}
	}

	public void actualiser() {	
		for (Iterator<Bonus> iterator = listeDeBonus.iterator(); iterator.hasNext();) {
			Bonus bonusActuel = iterator.next();

			if (bonusActuel.GetDuree() == 0) {
				iterator.remove();

			} else {
				if (bonusActuel.GetNom().compareTo("Sante") == 0) {
					this.vie += 30;
					bonusActuel.use();
					
				} else if (bonusActuel.GetNom().compareTo("Bouclier") == 0) {
					bonusActuel.use();
				}

			}
		}
	}

	public void tirer(ListeDeGraphiques listeDeGraphiques) {
		if (this.listeDeBonus.size() != 0) {
			for (Bonus bonusActuel : listeDeBonus) {
				if (bonusActuel.GetNom().compareTo("Ballefoistrois") == 0) {
					listeDeGraphiques.add(new Bullet("Bullet", this.GetX() - (int) (50 * Math.sin(this.getAngle() * Math.PI / 180)),
							this.GetY() - (int) (50 * Math.cos(this.getAngle() * Math.PI / 180)), 21, 21,
							this.getAngle(),this));
					
					listeDeGraphiques.add(new Bullet("Bullet", this.GetX(), this.GetY(), 21, 21, this.getAngle(),this));

					listeDeGraphiques.add(new Bullet("Bullet", this.GetX() + (int) (50 * Math.sin(this.getAngle() * Math.PI / 180)),
							this.GetY() + (int) (50 * Math.cos(this.getAngle() * Math.PI / 180)), 21, 21,
							this.getAngle(),this));
					
					bonusActuel.use();
				} else if (bonusActuel.GetNom().compareTo("BallePerforante") == 0) {
					listeDeGraphiques.add(new Bullet("BallePerforante", this.GetX(), this.GetY(), 21, 21, this.getAngle(),this));
					bonusActuel.use();
				} else {
					listeDeGraphiques.add(new Bullet("Bullet", this.GetX(), this.GetY(), 21, 21, this.getAngle(),this));
				}
			}
		} else {
			listeDeGraphiques.add(new Bullet("Bullet", this.GetX()+30, this.GetY()-50, 21, 21, this.getAngle(),this));
		}
	}

	public void reculer() {
		int new_x = this.GetX() - (int) (vitesse * Math.cos(this.getAngle() * Math.PI / 180));
		int new_y = this.GetY() - (int) (vitesse * Math.sin(this.getAngle() * Math.PI / 180));

		if (new_x > 0 && new_x < HAUTEUR_ECRAN-100) {
			this.setX(new_x);
		}
		if (new_y > 0 && new_y < LONGUEUR_ECRAN-100) {
			this.SetY(new_y);
		}
	}
}
