package radikalchess.model;

import radikalchess.model.pieces.Piece;

/**
 * A Transformer is responsible for transforming a piece in another.
 * It has a single public method that allows the impending transformation.
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 */
public interface Transformer {
    public void transform(Piece piece, Board board);
}
