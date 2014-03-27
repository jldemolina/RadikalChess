package model;

import model.Pieces.Piece;

public class Board {

    private Cell cells[][];

    public Board(int numberOfRows, int numberOfCols) {
        cells = new Cell[numberOfRows][numberOfCols];
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfCols; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public int getNumberOfRows() {
        return cells.length;
    }

    public int getNumberOfCols() {
        return cells[0].length;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Piece getPieceAt(Position position) {
        return cells[position.getRow()][position.getCol()].getPiece();
    }

    public void setPieceAt(Position position, Piece piece) {
        if (piece != null) piece.setPosition(position);
        cells[position.getRow()][position.getCol()].setPiece(piece);
    }

}
