package radikalchess.model.Pieces;

import radikalchess.model.Image;
import radikalchess.model.Position;

public abstract class Piece implements Cloneable {

    protected int color;
    protected Position position;
    protected Image image;

    public Piece(int color, Image image) {
        this.color = color;
        this.image = image;
    }

    public Piece(int color, Image image, Position position) {
        this(color, image);
        this.position = position;
    }

    public int getColor() {
        return color;
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

    public abstract int getStack();

    @Override
    public abstract Piece clone();

}
