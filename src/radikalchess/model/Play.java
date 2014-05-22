package radikalchess.model;

public class Play {
    private final Player player;
    private final int numberOfMovements;

    public Play(Player player, int numberOfMovements) {
        this.player = player;
        this.numberOfMovements = numberOfMovements;
    }

    public int getNumberOfMovements() {
        return numberOfMovements;
    }

    public Player getPlayer() {
        return player;
    }
}
