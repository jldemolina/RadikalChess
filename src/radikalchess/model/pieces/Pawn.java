package radikalchess.model.pieces;

import radikalchess.model.Image;
import radikalchess.model.Player;

public class Pawn extends Piece {

    private final AllowedPawnMove allowedPawnMove;

    public Pawn(Player player, Image image, AllowedPawnMove allowedPawnMove) {
        super(player, image);
        this.allowedPawnMove = allowedPawnMove;
    }

    public AllowedPawnMove getAllowedPawnMove() {
        return allowedPawnMove;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Piece piece = new Pawn(player, image, allowedPawnMove);
        piece.setPosition(position);
        return piece;
    }
}
