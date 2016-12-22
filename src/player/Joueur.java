package player;

import avion.Avion;


public class Joueur {
    private static int nombreDeConnecte  = 0;
    
    public  final static  int	  MAXIMUM = 1000;    

    private final  int    id;
    private final  String pseudo;
    private final  Avion  plane;
    private        int    score   = 0;
    
    
    public Joueur (String pseudo, String nomAvion) {
        this.id = nombreDeConnecte++;
        
        this.pseudo = pseudo;
        this.plane = new Avion(nomAvion, 0, 0, 100, 100);
    }
    
    public int getScore () {
    	return score;
    }
    
    public int getId() {
        return this.id;
    }

	public Avion getAvion() {
    	return this.plane;
    }
 
    public String [] getEtat() {
    	String x     = Integer.toString(this.plane.getX());
    	String y     = Integer.toString(this.plane.getY());
    	String angle = Double.toString(this.plane.getAngle());
    			
        String positions[] = {x, y, angle, this.getPseudo()};
        
        return positions;
    }

	public String getPseudo() {
		return this.pseudo;
	}
}
