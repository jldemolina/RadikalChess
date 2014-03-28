package radikalchess.model;

import radikalchess.model.Pieces.Piece;

public class Cell {

    private Piece piece;
    private final Position position;

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