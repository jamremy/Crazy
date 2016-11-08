package Base;

public class Graphique {
	private int x, y;
	private int height, width;
	private String nom;
	private int id;
	private static int increment = 0;
	public Graphique (String nom, int x, int y ,int width , int heigth)
	{
		this.x=(int) x;
		this.y=(int) y;
		this.nom=nom;
		this.width=width;
		this.height=height;
		this.id=++increment;
	}
	
	public String GetNom()
	{
		return nom;
	}
	public int GetId()
	{
		return id;
	}
	public void SetX(int x)
	{
		this.x=x;
	}
	public void SetY(int y)
	{
		this.y=y;
	}
	public int GetX()
	{
		return x;
	}
	public int GetY()
	{
		return y;
	}
	
	public boolean ColliDeRect(Graphique g)
	{
		 if((g.x >= x +width)      // trop à droite
				 	|| (g.x + g.width <= x) // trop à gauche

				  || (-g.y >= y + height) // trop en bas

				    || (-g.y + g.height <= y))  // trop en haut

				          return false; 
				   else
				          return true;
	}

}
