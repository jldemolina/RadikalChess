package radikalchess.model.pieces;

import radikalchess.model.Image;
import radikalchess.model.Player;

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
