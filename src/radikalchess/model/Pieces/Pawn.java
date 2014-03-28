package radikalchess.model.Pieces;

import radikalchess.model.Image;

public class Pawn extends Piece {

    public Pawn(int color, Image image) {
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
