package model;

import model.Pieces.*;

public class MoveChecker {
    public static MoveChecker INSTANCE;

    private MoveChecker() {
    }

    public static MoveChecker getInstance() {
        if (INSTANCE == null) INSTANCE = new MoveChecker();
        return INSTANCE;
    }

    public boolean isAValidMove(Move move, Piece piece, Board board) {
        if (piece instanceof Pawn) return isAValidPawnMove(move, board);
        if (piece instanceof Bishop) return isAValidBishopMove(move, board);
        if (piece instanceof Rook) return isAValidRookMove(move, board);
        if (piece instanceof Queen) return isAValidQueenMove(move, board);
        if (piece instanceof King) return isAValidKingMove(move, board);
        return false;
    }

    private boolean isAValidPawnMove(Move move, Board board) {
        return ((Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == 0 &&
                Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) == 1) ||
                (Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) == 0 &&
                        Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == 1));
    }

    private boolean isAValidBishopMove(Move move, Board board) {
        return false;
    }

    private boolean isAValidRookMove(Move move, Board board) {
        return false;
    }

    private boolean isAValidQueenMove(Move move, Board board) {
        return false;
    }

    private boolean isAValidKingMove(Move move, Board board) {
        return ((Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == 0 &&
                Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) == 1) ||
                (Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) == 0 &&
                        Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == 1));
    }


}
