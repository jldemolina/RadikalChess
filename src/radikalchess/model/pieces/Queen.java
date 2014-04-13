package radikalchess.model.pieces;

import radikalchess.model.Image;
import radikalchess.model.Player;

public class Queen extends Piece {

    public Queen(Player player, Image image) {
        super(player, image);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Piece piece = new Queen(player, image);
        piece.setPosition(position);
        return piece;
    }
}
