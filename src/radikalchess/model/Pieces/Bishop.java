package radikalchess.model.Pieces;

import radikalchess.model.Image;
import radikalchess.model.Position;

public class Bishop extends Piece {

    public Bishop(int color, Image image) {
        super(color, image);
    }

    public Bishop(int color, Image image, Position position) {
        super(color, image, position);
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
