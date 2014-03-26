package model.Pieces;

import model.Image;
import model.Position;

public abstract class Piece implements Cloneable {

    protected int color;
    protected Position position;
    protected Image image;

    public Piece(int color, Image image) {
        this.color = color;
        this.image = image;
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
