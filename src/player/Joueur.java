package player;
import airCraft.Avion;
import car.Voiture;


public class Joueur {
    private static int increment = 0;
    private final int id;
    private final String pseudo;
    private final Avion plane;
    private int score=0;
    public Joueur (String pseudo) 
    {
        id = ++increment;
        this.pseudo = pseudo;
        this.plane=new Avion("MIG-51S",0,0,100,100);
    }
    
    public int GetScore ()
    {
    	return score;
    }
    
    public int getId() 
    {
        return this.id;
    }
    
    public Avion GetAvion()
    {
    	return this.plane;
    }
 
    public double[] getPosition() 
    {
        double positions[] = {this.plane.GetX(), this.plane.GetY(), this.plane.getAngle()};
        return positions;
    }
}
