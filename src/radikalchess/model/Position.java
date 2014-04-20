package radikalchess.model;

/**
 * A position indicates a space on the game board.
 * A board is a numbered space and the enumeration corresponds to the relative positions.
 *
 * @author Jose Luis Molina
 */
public class Position implements Cloneable {
    private int col;
    private int row;

    /**
     * Create a position with one row and one column. Usually fit the same row and column of the table,
     * the cell and the piece
     *
     * @param row
     * @param col
     */
    public Position(int row, int col) {
        this.col = col;
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Get the Euclidean Distance to other position
     * The Euclidean distance or Euclidean metric is the "ordinary" distance between two points that
     * one would measure with a ruler, and is given by the Pythagorean formula
     *
     * @param position
     * @return
     */
    public double getEuclideanDistanceTo(Position position) {
        int x1 = this.getRow();
        int x2 = position.getRow();
        int y1 = this.getCol();
        int y2 = position.getCol();
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    @Override
    public Position clone() {
        return new Position(row, col);
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }

    public boolean equals(Position position) {
        return this.col == position.getCol() && this.row == position.getRow();
    }

}
