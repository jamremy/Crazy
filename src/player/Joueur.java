package player;
import car.Voiture;


public class Joueur {
    private static int increment = 0;
    private final int id;
    private final String pseudo;
    private final Voiture car;

    public Joueur (String pseudo) {
        id = ++increment;
        this.pseudo = pseudo;
        this.car = new Voiture();
    }

    public int getId() {
        return this.id;
    }

    public double[] getPosition() {
        double positions[] = {this.car.getX(), this.car.getY(), this.car.getAngle()};
        return positions;
    }
}
