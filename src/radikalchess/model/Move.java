package radikalchess.model;

/**
 * This class represents a movement. A movement permits, within the board, to move a piece
 * from one position to another.
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 * @see Position
 */
public class Move {

    private final Position origin;
    private final Position destination;

    /**
     * Initializes a movement formed by a source position and a target one
     *
     * @param origin
     * @param destination
     */
    public Move(Position origin, Position destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Position getOrigin() {
        return origin;
    }

    public Position getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Moving piece from " + origin.toString() + " to " + destination.toString();
    }

    @Override
    public boolean equals(Object object) {
        Move move = (Move) object;
        if (origin.equals(move.origin) && destination.equals(move.destination))
            return true;
        return false;
    }
}
