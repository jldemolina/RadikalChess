package radikalchess.model.pieces;

import radikalchess.model.Image;
import radikalchess.model.Player;

public class Bishop extends Piece {

    public Bishop(Player player, Image image) {
        super(player, image);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Piece piece = new Bishop(player, image);
        piece.setPosition(position);
        return piece;
    }
}
