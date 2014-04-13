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

    public double getEuclideanDistanceTo(Position position) {
        int x1 = this.getRow();
        int x2 = position.getRow();
        int y1 = this.getCol();
        int y2 = position.getCol();
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public Position move(int[] offset) {
        return new Position(this.getRow() + offset[0], this.getCol() + offset[1]);
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
