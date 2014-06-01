package radikalchess.model.pieces;

import radikalchess.model.Image;
import radikalchess.model.Player;

/**
 * This class represents a piece of board, and inherits from the class Piece
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 */
public class King extends Piece {
    private static final String name = "King";
    private static final double points = 100;

    public King(Player player, Image image) {
        super(player, image);
    }

    @Override
    public String toString() {
        return name + " [" + super.getPlayer().getName() + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Piece piece = new King(player, image);
        piece.setPosition(position);
        return piece;
    }

    @Override
    public int getPoints() {
        return (int) points;
    }
}
