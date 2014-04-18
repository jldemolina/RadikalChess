package radikalchess.model;

/**
 * A player is a person or machine that participates directly and actively in the game.
 * It has a name that generally should be unique.
 *
 * @author Jose Luis Molina
 */
public class Player {
    private String name;
    private int points;

    public Player(String name) {
        this.name = name;
        this.points = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public boolean equals(Object object) {
        return name == ((Player) object).name;
    }
}
