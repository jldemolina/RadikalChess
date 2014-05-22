package radikalchess.model;

import radikalchess.model.pieces.King;
import radikalchess.model.pieces.Piece;

/**
 * That represents the game board. The board is formed by cells, and these contain
 * the various pieces that make up the game
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 * @see radikalchess.model.Cell
 */
public class Board {

    private Cell cells[][];

    /**
     * Create a board with a specific number of rows and columns
     *
     * @param numberOfRows
     * @param numberOfCols
     */
    public Board(int numberOfRows, int numberOfCols) {
        cells = new Cell[numberOfRows][numberOfCols];
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfCols; j++) {
                cells[i][j] = new Cell(new Position(i, j));
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

    /**
     * Walking across the board and looking for the position of king of a player
     *
     * @param player
     * @return The position of the king
     */
    public Position searchKingPosition(Player player) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].getPiece() != null) {
                    if (cells[i][j].getPiece() instanceof King && (cells[i][j].getPiece().getPlayer().equals(player)))
                        return cells[i][j].getPiece().getPosition();
                }
            }
        }
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Board board = new Board(cells.length, cells[0].length);
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                board.cells[i][j] = new Cell(new Position(i, j));
                if (cells[i][j].getPiece() != null) board.cells[i][j].setPiece((Piece) cells[i][j].getPiece().clone());
            }
        }
        return board;
    }
}
