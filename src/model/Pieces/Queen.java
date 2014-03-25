package model.Pieces;

public class Queen extends Piece {

    public Queen(int color) {
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
