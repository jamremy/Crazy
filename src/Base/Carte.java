package Base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import AirCraft.Bullet;
import Bonus.ListeDeBonus;
import Bonus.bonus;
import Explosion.ListeDeExplosions;
import VaisseauEnnemi.BulletEnnemi;
import VaisseauEnnemi.Ennemi;
import VaisseauEnnemi.ListeDeEnnemis;
import player.Joueur;
import player.ListeDeJoueurs;

public class Carte {
	private ListeDeJoueurs ldj = new ListeDeJoueurs();
	private ListeDeGraphiques ldg = new ListeDeGraphiques();
	private ListeDeEnnemis lde=new ListeDeEnnemis();
	private ListeDeExplosions ldex =new ListeDeExplosions();
	private ListeDeBonus ldb=new ListeDeBonus();
	long TimeLastActualisation=0;
	char BufferMap[][]=new char[1000][];
	public Carte (String fichier)
	{
		
			int i=0;
			int x, y;
	       String chaine = "";
	       
	       //Lecture de la carte stocker en memoire
			try{
				InputStream ips=new FileInputStream(fichier); 
				InputStreamReader ipsr=new InputStreamReader(ips);
				BufferedReader br=new BufferedReader(ipsr);
				String ligne;
				while ((ligne=br.readLine())!=null){
					chaine+=ligne+"\n";
					
					if (i<1000)
					{
						BufferMap[i]=ligne.toCharArray();
						i++;
					}
				}
				
				
				br.close(); 
			}		
			catch (Exception e){
			}
			//Creation des graphiques associes a la carte
			
			for (i=0;i<1000;i++)
			{
				for (int j=0;j<10;j++)
				{
					if (BufferMap [i][j]!='A')
					{
						if (BufferMap [i][j]=='B')
						{
							this.lde.add(new Ennemi("ennemi1", i*-80, j*80, 100, 100));
						}
						else if(BufferMap [i][j]=='S')
						{
							this.ldb.add(new bonus("Sante",i*-80, j*80, 100, 100));
						}
						else if(BufferMap [i][j]=='T')
						{
							this.ldb.add(new bonus("BallePerforante",i*-80, j*80, 100, 100));
						}
						else if(BufferMap [i][j]=='M')
						{
							this.ldb.add(new bonus("Missile",i*-80, j*80, 100, 100));
						}
						else if(BufferMap [i][j]=='3')
						{
							this.ldb.add(new bonus("Ballefoistrois",i*-80, j*80, 100, 100));
						}
						else if(BufferMap [i][j]=='O')
						{
							this.ldb.add(new bonus("Bouclier",i*-80, j*80, 100, 100));
						}
						
						
					}
				}
			}
			
			
	}

	//Actualise la liste de graphique prśent sur la map
	public void Actualiser(long time)
	{
		if (Math.abs(time-TimeLastActualisation)>50)
		{
			lde.move_all(5,0);
			ldb.move_all(5,0);
			TimeLastActualisation=time;
		}
		System.out.println(time-TimeLastActualisation);
		
		ldg.Actualiser(time);
		lde.Actualiser(time,ldg,ldex);
		ldj.Actualiser(time);
		ldb.Actualiser(time);
		ldex.Actualiser(time);
	
	}
	
	public JSONObject GetAllGraphiquesPosition ()
	{
		JSONObject positions = new JSONObject();
		try {
			positions.accumulate("Carte", this.ldj.allPositions());
			positions.accumulate("Carte", this.ldg.allPositions());
			positions.accumulate("Carte", this.lde.allPositions());
			positions.accumulate("Carte", this.ldex.allPositions());
			positions.accumulate("Carte", this.ldb.allPositions());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return positions;
	}
	
	public ListeDeGraphiques GetListeDeGraphiques()
	{
		return ldg;
	}
	//Detecte et traite les collision entre les different objet present sur la carte
	public void DetectCollision()
	{
		
		for (bonus b:ldb)
		{
			for (Joueur j:ldj.GetListeJoueur())
			{
				// en cas de collision entre un avion et un bonus on affecte ce bonus a la liste des bonus de l'avion
				if (b.ColliDeRect(j.GetAvion())==true)
				{
					j.GetAvion().GetMesBonus().add(b);
					b.Set_DejaUtilise(true);
				}
			}
			
		}
		
		for (Graphique g :ldg )
		{
			for (Joueur j:ldj.GetListeJoueur())
			{
				// si il y a une collision entre un avion et un graphique present sur la map
				if (g.ColliDeRect(j.GetAvion())==true )
				{
					// sil sagit d'une balle ennemis
					if (g instanceof BulletEnnemi)
					{
						// blesser l'avion 
						j.GetAvion().Blesser((BulletEnnemi)g);
						//Detruire le balle
						g.SetX(-500);
					}
					
				}
			}
		}
		for (Ennemi e : lde)
		{
			for (Joueur j:ldj.GetListeJoueur())
			{
				// en cas de collision entre un ennemi et un avion on detruit lennemi et on retire des point de vie a l'avion
				if (e.ColliDeRect(j.GetAvion())==true )
				{
					e.Blesser(new Bullet("DeathBullet",0,0,100,100,20));
					j.GetAvion().Blesser(new Bullet("BallePerforante",0,0,100,100,20));
				}
			}
		}
		
		for (Graphique g :ldg )
		{
			for (Ennemi e : lde)
			{
				// si il y a une collision entre un ennemi et un graphique present sur la map
				if (g.ColliDeRect(e)==true || e.ColliDeRect(g)==true)
				{
					if (g instanceof BulletEnnemi)
					{
						
					}
					// sil sagit d'une balle 
					else if (g instanceof Bullet)
					{
						// blesser l'ennemis
					e.Blesser((Bullet)g);
					//Detruire le balle 
					g.SetX(-500);
					
					}
				}
			}
		}
	}

	public ListeDeJoueurs GetListeDeJoueurs()
	{
		return ldj;
	}
	
	public ListeDeEnnemis GetListeDeEnnemis ()
	{
		return lde;
	}
}
	
