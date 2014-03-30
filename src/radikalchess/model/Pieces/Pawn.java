package radikalchess.model.Pieces;

import radikalchess.model.Image;
import radikalchess.model.Position;

public class Pawn extends Piece {

    public Pawn(int color, Image image) {
        super(color, image);
    }

    public Pawn(int color, Image image, Position position) {
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
