package radikalchess.model;

import radikalchess.model.pieces.Piece;

/**
 * This class represents a cell. A cell is an area listed within the board, and contains at most one piece.
 * As is numbered, has a defined position
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 * @see radikalchess.model.pieces.Piece
 * @see radikalchess.model.Position
 */
public class Cell {

    private Piece piece;
    private final Position position;

    /**
     * Sets the position of a cell. Generally added in a board, in the same relative position
     *
     * @param position
     */
    public Cell(Position position) {
        this.position = position;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Position getPosition() {
        return position;
    }


}