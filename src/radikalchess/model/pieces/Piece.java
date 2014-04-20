package radikalchess.model.pieces;

import radikalchess.model.Image;
import radikalchess.model.Player;
import radikalchess.model.Position;

/**
 * This class represents a game piece. It is an abstract class that inherits all the pieces that can be placed on the board.
 * A piece have an player, an image, and a position.
 *
 * @author Jose Luis Molina
 */
public abstract class Piece implements Cloneable {

    protected Player player;
    protected Position position;
    protected Image image;

    /**
     * @param player The player who owns the piece
     * @param image  The picture represents the part
     */
    public Piece(Player player, Image image) {
        this.player = player;
        this.image = image;
    }

    public Player getPlayer() {
        return player;
    }

    public Position getPosition() {
        return position;
    }

    public Image getImage() {
        return image;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Piece) {
            Piece piece = (Piece) object;
            if (piece.image.getBitmap().getByteArray() == image.getBitmap().getByteArray()
                    && piece.position.equals(position) && piece.player.equals(player)) return true;
        }
        return false;
    }

    @Override
    public abstract String toString();

    @Override
    public abstract Object clone() throws CloneNotSupportedException;

    /**
     * @return The points of the piece
     */
    public abstract int getPoints();
}
