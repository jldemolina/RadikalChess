package radikalchess.model.pieces;

import radikalchess.model.Image;
import radikalchess.model.Player;
import radikalchess.model.Position;

public abstract class Piece implements Cloneable {

    protected Player player;
    protected Position position;
    protected Image image;

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
    public abstract Object clone() throws CloneNotSupportedException;
}
