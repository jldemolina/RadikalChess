package radikalchess.model;

import radikalchess.model.pieces.Piece;

public interface Transformer {
    public void transform(Piece piece, Board board);
}
