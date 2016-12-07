package airCraft;

import java.util.Iterator;

import base.Graphique;
import base.ListeDeGraphiques;
import bonus.Bonus;
import bonus.ListeDeBonus;

public class Avion extends Graphique{
	public static final double ANGLE = 2;
	
	private double vitesse;
	private double  vie;
	private String Bonus;
	private int attaque;
	private ListeDeBonus MesBonus=new ListeDeBonus();
	private int defense;
	public Avion(String Nom, int x, int y, int width,int height) {
		super(Nom,x,y,width,height);
		this.SetAngle(0);
		/*this.SetX(-50);
		this.SetY(50);*/
		if (Nom.equals("MIG-51S"))
		{
			this.vie=1000;
			this.vitesse=10;
			this.attaque=15;
			this.defense=30;
		}
		else if (Nom.equals("Su-55"))
		{
			this.vie=1000;
			this.vitesse=10;
			this.attaque=15;
			this.defense=30;
		}
		else if (Nom.equals("Su-37K"))
		{
			this.vie=1000;
			this.vitesse=10;
			this.attaque=15;
			this.defense=30;
		}
	}

	public ListeDeBonus GetMesBonus()
	{
		return MesBonus;
	}
	public void tournerGauche() {
		
		this.SetAngle(this.getAngle()+ANGLE);
	
	}
	public void SetVie(double vie)
	{
		this.vie=vie;
	}
	public void  SetAttaque (int attaque)
	{
		this.attaque=attaque;
	}
	public void SetDefense (int defense)
	{
		this.defense=defense;
	}
	public void SetVitesse(double vitesse)
	{
		this.vitesse=vitesse;
	}
	public double GetVie()
	{
		return this.vie;
	}
	public void tournerDroite() {
		this.SetAngle(this.getAngle()-ANGLE);
	}
	public void Blesser(Bullet balle)
	{
		if (this.MesBonus.size()!=0)
		{
			if (this.MesBonus.get(0).GetNom().compareTo("Bouclier")!=0)
			{
				this.SetVie(this.GetVie()-balle.GetDegat());
			}
		}
		else 
		{
			this.SetVie(this.GetVie()-balle.GetDegat());
		}
		if (balle.GetNom().compareTo("DeathBullet")==0)
		{
			this.vie=0;
		}
		
	}
	public void avancer() {
		
			
			
			int new_x=this.GetX()+ (int)(vitesse*Math.cos(this.getAngle()*Math.PI/180));
			if (new_x>0 && new_x<600)
			{
				this.SetX(new_x);
			}
			int new_y=this.GetY()+ (int)(vitesse*Math.sin(this.getAngle()*Math.PI/180));
			
			if (new_y>0 && new_y<700)
			{
			this.SetY(new_y);
			}
	}
	public void Actualiser()
	{
		int i=0;
		for (Iterator<Bonus> iterator = MesBonus.iterator(); iterator.hasNext(); ) {
			Bonus b = iterator.next();
			
			if (b.GetDuree()==0 )
			{
				iterator.remove();
				
			}
			else 
			{
				
					if (b.GetNom().compareTo("Sante")==0)
					{
						this.vie+=30;
						b.use();
					}
					else if (b.GetNom().compareTo("Bouclier")==0)
					{
						b.use();
					}
					
			}
		}
	}
	public void tirer (ListeDeGraphiques ldg)
	{
		if (this.MesBonus.size()!=0)
		{
			for (Bonus b: MesBonus)
			{
			if  (b.GetNom().compareTo("Ballefoistrois")==0)
			{
				ldg.add(new Bullet("Bullet",this.GetX()-(int)(50*Math.sin(this.getAngle()*Math.PI/180)),this.GetY()-(int)(50*Math.cos(this.getAngle()*Math.PI/180)),21,21,this.getAngle())); 
				ldg.add(new Bullet("Bullet",this.GetX(),this.GetY(),21,21,this.getAngle())); 
				
				ldg.add(new Bullet("Bullet",this.GetX()+(int)(50*Math.sin(this.getAngle()*Math.PI/180)),this.GetY()+(int)(50*Math.cos(this.getAngle()*Math.PI/180)),21,21,this.getAngle())); 
				b.use();
			}
			else if (b.GetNom().compareTo("BallePerforante")==0)
			{
				ldg.add(new Bullet("BallePerforante",this.GetX(),this.GetY(),21,21,this.getAngle())); 
				b.use();
			}
			else 
			{
				ldg.add(new Bullet("Bullet",this.GetX(),this.GetY(),21,21,this.getAngle())); 
			}
		}
		}
		
		else
		{
		ldg.add(new Bullet("Bullet",this.GetX(),this.GetY(),21,21,this.getAngle())); 
		}
	}
	
	public void reculer()
	{	
			int new_x=this.GetX()- (int)(vitesse*Math.cos(this.getAngle()*Math.PI/180));
			if (new_x>0 && new_x<600)
			{
				this.SetX(new_x);
			}
			int new_y=this.GetY()- (int)(vitesse*Math.sin(this.getAngle()*Math.PI/180));
			if (new_y>0 && new_y<700)
			{
			this.SetY(new_y);
			}	
	}
}
