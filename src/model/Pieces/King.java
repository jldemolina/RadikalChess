package model.Pieces;

public class King extends Piece {

    public King(int color) {
        super(color);
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
