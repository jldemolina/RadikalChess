package model.Pieces;

public class Bishop extends Piece {

    public Bishop(int color) {
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
