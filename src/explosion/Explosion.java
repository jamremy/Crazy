package explosion;

import carte.Graphique;

public class Explosion extends Graphique{

	private int NumImage = 0;
	private int NbImage; //Nombre d'image  que 
	public Explosion(String nom, int x, int y, int width, int height) {
		super(nom, x, y, width, height);
		this.SetFrequenceActualisation(100);
		if (nom.compareTo("Explosion1")==0)
		{
			this.NbImage=31;
		}
		// TODO Auto-generated constructor stub
	}
	public int GetNumImage ()
	{
		return NumImage;
	}
	
	public int GetNbImage ()
	{
		return NbImage;
	}
	
	public void Actualiser(long time)
	{
	    // Si le temps est un multiple de deux alors la balle avance
		if (this.NumImage<this.NbImage)
		{
			this.NumImage+=1;
		}
			this.SetTimeLastActualisation(time);
		
	}
	
	
}
