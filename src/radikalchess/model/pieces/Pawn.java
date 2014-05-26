package radikalchess.model.pieces;

import radikalchess.model.Image;
import radikalchess.model.Player;

/**
 * This class represents a piece of board, and inherits from the class Piece
 *
 * @author Jose Luis Molina
 * @see radikalchess.model.pieces.AllowedPawnMove
 */
public class Pawn extends Piece {
    private static final String name = "Pawn";
    private static final int points = 2;
    private final AllowedPawnMove allowedPawnMove;

    /**
     * Initializes the pawn
     *
     * @param player          The player who owns the piece
     * @param image           The picture represents the part
     * @param allowedPawnMove The direction of movement allowed for the pawn
     */
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

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Pawn) {
            Pawn piece = (Pawn) object;
            if (piece.image.getBitmap().getByteArray() == image.getBitmap().getByteArray()
                    && piece.position.equals(position) && piece.player.equals(player)
                    && piece.allowedPawnMove == allowedPawnMove) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return name + " [" + super.getPlayer().getName() + "]";
    }

}
