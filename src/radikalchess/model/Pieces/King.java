package radikalchess.model.Pieces;

import radikalchess.model.Image;

public class King extends Piece {

    public King(int color, Image image) {
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
