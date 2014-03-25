package model.Pieces;

public class Pawn extends Piece {

    public Pawn(int color) {
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
