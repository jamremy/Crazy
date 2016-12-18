package vaisseauEnnemi;

import carte.ListeDeGraphiques;
import airCraft.Avion;
import airCraft.Bullet;

public class Ennemi extends Avion{

	public Ennemi(String Nom, int x, int y, int width, int height) {
		super(Nom, x, y, width, height);
		if (Nom.compareTo("ennemi1")==0)
		{
			this.SetVie(30);
			this.SetAttaque(20);
			this.SetDefense(10);
			this.SetVitesse(10);
			this.SetScore(50);
			this.SetFrequenceActualisation(3000);
			
		}
		else if (Nom.compareTo("ennemi2")==0)
		{
			this.SetVie(150);
			this.SetAttaque(20);
			this.SetDefense(10);
			this.SetScore(70);
			this.SetVitesse(20);
			this.SetFrequenceActualisation(3000);
		} 
		else if (Nom.compareTo("ennemi3")==0)
		{
			this.SetVie(300);
			this.SetAttaque(20);
			this.SetDefense(10);
			this.SetScore(30);
			this.SetVitesse(10);
			this.SetFrequenceActualisation(3000);
		}  
		else if (Nom.compareTo("ennemi4")==0)
		{
			this.SetVie(30);
			this.SetAttaque(20);
			this.SetScore(100);
			this.SetDefense(10);
			this.SetVitesse(10);
		} 
		
		
		
		// TODO Auto-generated constructor stub
	}
	
	
	public void tirer(ListeDeGraphiques ldg)
	{
		
		//Definition d'un comportement  de tire pour lennemi1
		if (this.GetNom().compareTo("ennemi1")==0)
		{
		ldg.add(new BulletEnnemi(this.GetX(),this.GetY(),-180));
		ldg.add(new BulletEnnemi(this.GetX(),this.GetY(),-90));
		//ldg.add(new BulletEnnemi(this.GetX(),this.GetY(),-45));
		}
		//Definition d'un comportement  de tire pour lennemi2
		else if (this.GetNom().compareTo("ennemi2")==0)
		{
			ldg.add(new BulletEnnemi(this.GetX(),this.GetY(),+90));
			ldg.add(new BulletEnnemi(this.GetX(),this.GetY(),-90));
		}
		else if (this.GetNom().compareTo("ennemi3")==0)
		{
			ldg.add(new BulletEnnemi(this.GetX(),this.GetY(),-70));
			ldg.add(new BulletEnnemi(this.GetX(),this.GetY(),+70));
		}
	}
	
	public void Actualiser(long time,ListeDeGraphiques ldg)
	{
				this.tirer(ldg);
				this.SetTimeLastActualisation(time);	
	}

}
