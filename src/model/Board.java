package model;

import model.Pieces.Piece;

public class Board {

    private Cell cells[][];

    public Board(int numberOfRows, int numberOfCols) {
        cells = new Cell[numberOfCols][numberOfRows];
        for (int i = 0; i < numberOfCols; i++) {
            for (int j = 0; j < numberOfRows; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public int getNumberOfRows() {
        return cells[0].length;
    }

    public int getNumberOfCols() {
        return cells.length;
    }

    public Piece getPieceAt(Position position) {
        return cells[position.getRow()][position.getCol()].getPiece();
    }

    public void setPieceAt(Position position, Piece piece) {
        if (piece != null) piece.setPosition(position);
        cells[position.getRow()][position.getCol()].setPiece(piece);
    }

}
