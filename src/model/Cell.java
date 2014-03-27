package model;

import model.Pieces.Piece;

public class Cell implements Cloneable {

    private Piece piece;

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public Cell clone() {
        Cell cell = new Cell();
        if (piece != null) cell.setPiece(this.getPiece().clone());
        return cell;
    }

}