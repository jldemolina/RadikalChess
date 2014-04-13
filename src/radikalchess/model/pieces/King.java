package radikalchess.model.pieces;

import radikalchess.model.Image;
import radikalchess.model.Player;

/**
 * This class represents a piece of board, and inherits from the class Piece
 *
 * @author Jose Luis Molina
 */
public class King extends Piece {

    public King(Player player, Image image) {
        super(player, image);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Piece piece = new King(player, image);
        piece.setPosition(position);
        return piece;
    }
}
