package model.Pieces;

import model.Image;

public class Queen extends Piece {

    public Queen(int color, Image image) {
        super(color, image);
    }

    @Override
    public int getStack() {
        return 0;
    }

    @Override
    public Piece clone() {
        return null;
    }
}
