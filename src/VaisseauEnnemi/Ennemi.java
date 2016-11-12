package VaisseauEnnemi;

import AirCraft.Avion;
import AirCraft.Bullet;

public class Ennemi extends Avion{

	public Ennemi(String Nom, int x, int y, int width, int height) {
		super(Nom, x, y, width, height);
		if (Nom.compareTo("ennemi1")==0)
		{
			this.SetVie(30);
			this.SetAttaque(20);
			this.SetDefense(10);
			this.SetVitesse(10);
			
		}
		else if (Nom.compareTo("ennemi2")==0)
		{
			this.SetVie(30);
			this.SetAttaque(20);
			this.SetDefense(10);
			this.SetVitesse(10);
		} 
		
		
		// TODO Auto-generated constructor stub
	}
	public void Blesser(Bullet balle)
	{
	
			this.SetVie(this.GetVie()-balle.GetDegat());
	}
	
	public void Actualiser(long time)
	{
	
	}

}
