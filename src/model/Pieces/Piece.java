package model.Pieces;

import model.Position;

public abstract class Piece implements Cloneable {

    protected int color;
    protected Position position;

    public Piece(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract int getStack();

    @Override
    public abstract Piece clone();

}
