package radikalchess.model.pieces;

import radikalchess.model.Image;
import radikalchess.model.Player;

public class Rook extends Piece {

    public Rook(Player player, Image image) {
        super(player, image);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Piece piece = new Rook(player, image);
        piece.setPosition(position);
        return piece;
    }
}
