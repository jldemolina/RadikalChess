package radikalchess.model.checkers;

import radikalchess.model.Board;
import radikalchess.model.Position;
import radikalchess.model.pieces.*;

import java.util.ArrayList;

/**
 * This is a special class of checking (respects the Singleton pattern) giving the attack range of a piece
 * in a certain position. Likewise, and from this, you can also get the destructible ("Killable") pieces
 *
 * @author Jose Luis Molina
 */
public class PieceAttackRangeChecker {
    public static PieceAttackRangeChecker INSTANCE;

    private PieceAttackRangeChecker() {
    }

    public static PieceAttackRangeChecker getInstance() {
        if (INSTANCE == null) INSTANCE = new PieceAttackRangeChecker();
        return INSTANCE;
    }

    public Piece[] getKillablePiecesFor(Piece piece, Board board) {
        if (piece instanceof King) return getKillablePiecesForKing(piece, board);
        else {
            ArrayList<Piece> pieces = new ArrayList<Piece>();
            for (Position position : getAttackRangeFor(piece, board))
                if (board.getPieceAt(position) != null) pieces.add(board.getPieceAt(position));
            return pieces.toArray(new Piece[0]);
        }
    }

    public Position[] getAttackRangeFor(Piece piece, Board board) {
        if (piece instanceof Pawn) return getAttackRangeForPawn((Pawn) piece, board);
        if (piece instanceof Bishop) return getAttackRangeForBishop(piece, board);
        if (piece instanceof Rook) return getAttackRangeForRook(piece, board);
        if (piece instanceof Queen) return getAttackRangeForQueen(piece, board);
        if (piece instanceof King) return discardKillablePositions(piece, getAttackRangeForKing(piece, board), board);
        return new Position[0];
    }

    public boolean isKillable(Piece piece, Board board) {
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                for (Piece killablePiece : getKillablePiecesFor(board.getPieceAt(new Position(i, j)), board)) {
                    if (killablePiece == piece) return true;
                }
            }
        }
        return false;
    }

    public boolean isKillable(Piece piece, Position destination, Board board) {
        boolean killable = false;

        Piece clone = null;
        try {
            clone = (Piece) piece.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        board.setPieceAt(piece.getPosition(), null);

        Piece killablePiece = board.getPieceAt(destination);
        piece.setPosition(destination);
        board.setPieceAt(destination, piece);
        if (isKillable(piece, board)) killable = true;
        board.setPieceAt(destination, killablePiece);
        board.setPieceAt(clone.getPosition(), clone);

        return killable;
    }

    private Piece[] getKillablePiecesForKing(Piece piece, Board board) {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        if (piece.getPosition().getRow() - 1 >= 0 && piece.getPosition().getCol() + 1 < board.getNumberOfCols()) {
            if (board.getCells()[piece.getPosition().getRow() - 1][piece.getPosition().getCol() + 1].getPiece() != null) {
                if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() - 1, piece.getPosition().getCol() + 1)))
                    pieces.add(board.getCells()[piece.getPosition().getRow() - 1][piece.getPosition().getCol() + 1].getPiece());
            }
        }
        if (piece.getPosition().getRow() - 1 >= 0 && piece.getPosition().getCol() - 1 >= 0) {
            if (board.getCells()[piece.getPosition().getRow() - 1][piece.getPosition().getCol() - 1].getPiece() != null) {
                if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() - 1, piece.getPosition().getCol() - 1)))
                    pieces.add(board.getCells()[piece.getPosition().getRow() - 1][piece.getPosition().getCol() - 1].getPiece());
            }
        }
        if (piece.getPosition().getRow() + 1 < board.getNumberOfRows() && piece.getPosition().getCol() + 1 < board.getNumberOfCols()) {
            if (board.getCells()[piece.getPosition().getRow() + 1][piece.getPosition().getCol() + 1].getPiece() != null) {
                if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() + 1, piece.getPosition().getCol() + 1)))
                    pieces.add(board.getCells()[piece.getPosition().getRow() + 1][piece.getPosition().getCol() + 1].getPiece());
            }
        }
        if (piece.getPosition().getRow() + 1 < board.getNumberOfRows() && piece.getPosition().getCol() - 1 >= 0) {
            if (board.getCells()[piece.getPosition().getRow() + 1][piece.getPosition().getCol() - 1].getPiece() != null) {
                if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() + 1, piece.getPosition().getCol() - 1)))
                    pieces.add(board.getCells()[piece.getPosition().getRow() + 1][piece.getPosition().getCol() - 1].getPiece());
            }
        }
        if (piece.getPosition().getRow() + 1 < board.getNumberOfRows()) {
            if (board.getCells()[piece.getPosition().getRow() + 1][piece.getPosition().getCol()].getPiece() != null) {
                if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() + 1, piece.getPosition().getCol())))
                    pieces.add(board.getCells()[piece.getPosition().getRow() + 1][piece.getPosition().getCol()].getPiece());
            }
        }
        if (piece.getPosition().getRow() - 1 >= 0) {
            if (board.getCells()[piece.getPosition().getRow() - 1][piece.getPosition().getCol()].getPiece() != null) {
                if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() - 1, piece.getPosition().getCol())))
                    pieces.add(board.getCells()[piece.getPosition().getRow() - 1][piece.getPosition().getCol()].getPiece());
            }
        }
        if (piece.getPosition().getCol() + 1 < board.getNumberOfCols()) {
            if (board.getCells()[piece.getPosition().getRow()][piece.getPosition().getCol() + 1].getPiece() != null) {
                if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow(), piece.getPosition().getCol() + 1)))
                    pieces.add(board.getCells()[piece.getPosition().getRow()][piece.getPosition().getCol() + 1].getPiece());
            }
        }
        if (piece.getPosition().getCol() - 1 >= 0) {
            if (board.getCells()[piece.getPosition().getRow()][piece.getPosition().getCol() - 1].getPiece() != null) {
                if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow(), piece.getPosition().getCol() - 1)))
                    pieces.add(board.getCells()[piece.getPosition().getRow()][piece.getPosition().getCol() - 1].getPiece());
            }
        }
        return pieces.toArray(new Piece[0]);
    }

    private Position[] getAttackRangeForPawn(Pawn pawn, Board board) {
        ArrayList<Position> positions = new ArrayList<Position>();
        if (pawn.getAllowedPawnMove().equals(AllowedPawnMove.UP)) {
            if (pawn.getPosition().getRow() - 1 >= 0 && pawn.getPosition().getCol() + 1 < board.getNumberOfCols()) {
                if (board.getCells()[pawn.getPosition().getRow() - 1][pawn.getPosition().getCol() + 1].getPiece() == null)
                    positions.add(new Position(pawn.getPosition().getRow() - 1, pawn.getPosition().getCol() + 1));
                else if (!arePiecesOfSamePlayer(board, pawn.getPosition(), new Position(pawn.getPosition().getRow() - 1, pawn.getPosition().getCol() + 1)))
                    positions.add(new Position(pawn.getPosition().getRow() - 1, pawn.getPosition().getCol() + 1));
            }
            if (pawn.getPosition().getRow() - 1 >= 0 && pawn.getPosition().getCol() - 1 >= 0) {
                if (board.getCells()[pawn.getPosition().getRow() - 1][pawn.getPosition().getCol() - 1].getPiece() == null)
                    positions.add(new Position(pawn.getPosition().getRow() - 1, pawn.getPosition().getCol() - 1));
                else if (!arePiecesOfSamePlayer(board, pawn.getPosition(), new Position(pawn.getPosition().getRow() - 1, pawn.getPosition().getCol() - 1)))
                    positions.add(new Position(pawn.getPosition().getRow() - 1, pawn.getPosition().getCol() - 1));
            }
        } else {
            if (pawn.getPosition().getRow() + 1 < board.getNumberOfRows() && pawn.getPosition().getCol() + 1 < board.getNumberOfCols()) {
                if (board.getCells()[pawn.getPosition().getRow() + 1][pawn.getPosition().getCol() + 1].getPiece() == null)
                    positions.add(new Position(pawn.getPosition().getRow() + 1, pawn.getPosition().getCol() + 1));
                else if (!arePiecesOfSamePlayer(board, pawn.getPosition(), new Position(pawn.getPosition().getRow() + 1, pawn.getPosition().getCol() + 1)))
                    positions.add(new Position(pawn.getPosition().getRow() + 1, pawn.getPosition().getCol() + 1));
            }
            if (pawn.getPosition().getRow() + 1 < board.getNumberOfRows() && pawn.getPosition().getCol() - 1 >= 0) {
                if (board.getCells()[pawn.getPosition().getRow() + 1][pawn.getPosition().getCol() - 1].getPiece() == null)
                    positions.add(new Position(pawn.getPosition().getRow() + 1, pawn.getPosition().getCol() - 1));
                else if (!arePiecesOfSamePlayer(board, pawn.getPosition(), new Position(pawn.getPosition().getRow() + 1, pawn.getPosition().getCol() - 1)))
                    positions.add(new Position(pawn.getPosition().getRow() + 1, pawn.getPosition().getCol() - 1));
            }
        }
        return positions.toArray(new Position[0]);
    }

    private Position[] getAttackRangeForBishop(Piece piece, Board board) {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = 1; i < board.getNumberOfRows(); i++) {
            if (piece.getPosition().getRow() + i >= board.getNumberOfRows() || piece.getPosition().getCol() + i >= board.getNumberOfCols())
                break;
            if (board.getCells()[piece.getPosition().getRow() + i][piece.getPosition().getCol() + i].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow() + i, piece.getPosition().getCol() + i));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() + i, piece.getPosition().getCol() + i))) {
                positions.add(new Position(piece.getPosition().getRow() + i, piece.getPosition().getCol() + i));
                break;
            } else
                break;
        }
        for (int i = 1; i < board.getNumberOfRows(); i++) {
            if (piece.getPosition().getRow() + i >= board.getNumberOfRows() || piece.getPosition().getCol() - i < 0)
                break;
            if (board.getCells()[piece.getPosition().getRow() + i][piece.getPosition().getCol() - i].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow() + i, piece.getPosition().getCol() - i));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() + i, piece.getPosition().getCol() - i))) {
                positions.add(new Position(piece.getPosition().getRow() + i, piece.getPosition().getCol() - i));
                break;
            } else
                break;
        }
        for (int i = 1; i < board.getNumberOfRows(); i++) {
            if (piece.getPosition().getRow() - i < 0 || piece.getPosition().getCol() + i >= board.getNumberOfCols())
                break;
            if (board.getCells()[piece.getPosition().getRow() - i][piece.getPosition().getCol() + i].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow() - i, piece.getPosition().getCol() + i));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() - i, piece.getPosition().getCol() + i))) {
                positions.add(new Position(piece.getPosition().getRow() - i, piece.getPosition().getCol() + i));
                break;
            } else
                break;
        }
        for (int i = 1; i < board.getNumberOfRows(); i++) {
            if (piece.getPosition().getRow() - i < 0 || piece.getPosition().getCol() - i < 0) break;
            if (board.getCells()[piece.getPosition().getRow() - i][piece.getPosition().getCol() - i].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow() - i, piece.getPosition().getCol() - i));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() - i, piece.getPosition().getCol() - i))) {
                positions.add(new Position(piece.getPosition().getRow() - i, piece.getPosition().getCol() - i));
                break;
            } else
                break;
        }
        return positions.toArray(new Position[0]);
    }

    private Position[] getAttackRangeForRook(Piece piece, Board board) {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (int i = 1; i < board.getNumberOfRows(); i++) {
            if (piece.getPosition().getRow() + i >= board.getNumberOfRows()) break;
            if (board.getCells()[piece.getPosition().getRow() + i][piece.getPosition().getCol()].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow() + i, piece.getPosition().getCol()));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() + i, piece.getPosition().getCol()))) {
                positions.add(new Position(piece.getPosition().getRow() + i, piece.getPosition().getCol()));
                break;
            } else
                break;
        }
        for (int i = 1; i < board.getNumberOfRows(); i++) {
            if (piece.getPosition().getRow() - i < 0) break;
            if (board.getCells()[piece.getPosition().getRow() - i][piece.getPosition().getCol()].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow() - i, piece.getPosition().getCol()));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() - i, piece.getPosition().getCol()))) {
                positions.add(new Position(piece.getPosition().getRow() - i, piece.getPosition().getCol()));
                break;
            } else
                break;
        }
        for (int i = 1; i < board.getNumberOfCols(); i++) {
            if (piece.getPosition().getCol() + i >= board.getNumberOfCols()) break;
            if (board.getCells()[piece.getPosition().getRow()][piece.getPosition().getCol() + i].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow(), piece.getPosition().getCol() + i));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow(), piece.getPosition().getCol() + i))) {
                positions.add(new Position(piece.getPosition().getRow(), piece.getPosition().getCol() + i));
                break;
            } else
                break;
        }
        for (int i = 1; i < board.getNumberOfCols(); i++) {
            if (piece.getPosition().getCol() - i < 0) break;
            if (board.getCells()[piece.getPosition().getRow()][piece.getPosition().getCol() - i].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow(), piece.getPosition().getCol() - i));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow(), piece.getPosition().getCol() - i))) {
                positions.add(new Position(piece.getPosition().getRow(), piece.getPosition().getCol() - i));
                break;
            } else
                break;
        }
        return positions.toArray(new Position[0]);
    }

    private Position[] getAttackRangeForQueen(Piece piece, Board board) {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (Position position : getAttackRangeForRook(piece, board))
            positions.add(position);
        for (Position position : getAttackRangeForBishop(piece, board))
            positions.add(position);
        return positions.toArray(new Position[0]);
    }

    private Position[] getAttackRangeForKing(Piece piece, Board board) {
        ArrayList<Position> positions = new ArrayList<Position>();
        if (piece.getPosition().getRow() - 1 >= 0 && piece.getPosition().getCol() + 1 < board.getNumberOfCols()) {
            if (board.getCells()[piece.getPosition().getRow() - 1][piece.getPosition().getCol() + 1].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow() - 1, piece.getPosition().getCol() + 1));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() - 1, piece.getPosition().getCol() + 1)))
                positions.add(new Position(piece.getPosition().getRow() - 1, piece.getPosition().getCol() + 1));
        }
        if (piece.getPosition().getRow() - 1 >= 0 && piece.getPosition().getCol() - 1 >= 0) {
            if (board.getCells()[piece.getPosition().getRow() - 1][piece.getPosition().getCol() - 1].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow() - 1, piece.getPosition().getCol() - 1));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() - 1, piece.getPosition().getCol() - 1)))
                positions.add(new Position(piece.getPosition().getRow() - 1, piece.getPosition().getCol() - 1));
        }
        if (piece.getPosition().getRow() + 1 < board.getNumberOfRows() && piece.getPosition().getCol() + 1 < board.getNumberOfCols()) {
            if (board.getCells()[piece.getPosition().getRow() + 1][piece.getPosition().getCol() + 1].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow() + 1, piece.getPosition().getCol() + 1));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() + 1, piece.getPosition().getCol() + 1)))
                positions.add(new Position(piece.getPosition().getRow() + 1, piece.getPosition().getCol() + 1));
        }
        if (piece.getPosition().getRow() + 1 < board.getNumberOfRows() && piece.getPosition().getCol() - 1 >= 0) {
            if (board.getCells()[piece.getPosition().getRow() + 1][piece.getPosition().getCol() - 1].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow() + 1, piece.getPosition().getCol() - 1));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() + 1, piece.getPosition().getCol() - 1)))
                positions.add(new Position(piece.getPosition().getRow() + 1, piece.getPosition().getCol() - 1));
        }
        if (piece.getPosition().getRow() + 1 < board.getNumberOfRows()) {
            if (board.getCells()[piece.getPosition().getRow() + 1][piece.getPosition().getCol()].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow() + 1, piece.getPosition().getCol()));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() + 1, piece.getPosition().getCol())))
                positions.add(new Position(piece.getPosition().getRow() + 1, piece.getPosition().getCol()));
        }
        if (piece.getPosition().getRow() - 1 >= 0) {
            if (board.getCells()[piece.getPosition().getRow() - 1][piece.getPosition().getCol()].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow() - 1, piece.getPosition().getCol()));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow() - 1, piece.getPosition().getCol())))
                positions.add(new Position(piece.getPosition().getRow() - 1, piece.getPosition().getCol()));
        }
        if (piece.getPosition().getCol() + 1 < board.getNumberOfCols()) {
            if (board.getCells()[piece.getPosition().getRow()][piece.getPosition().getCol() + 1].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow(), piece.getPosition().getCol() + 1));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow(), piece.getPosition().getCol() + 1)))
                positions.add(new Position(piece.getPosition().getRow(), piece.getPosition().getCol() + 1));
        }
        if (piece.getPosition().getCol() - 1 >= 0) {
            if (board.getCells()[piece.getPosition().getRow()][piece.getPosition().getCol() - 1].getPiece() == null)
                positions.add(new Position(piece.getPosition().getRow(), piece.getPosition().getCol() - 1));
            else if (!arePiecesOfSamePlayer(board, piece.getPosition(), new Position(piece.getPosition().getRow(), piece.getPosition().getCol() - 1)))
                positions.add(new Position(piece.getPosition().getRow(), piece.getPosition().getCol() - 1));
        }
        return positions.toArray(new Position[0]);
    }

    private Position[] discardKillablePositions(Piece piece, Position[] positions, Board board) {
        ArrayList<Position> unKillablePositions = new ArrayList<Position>();
        for (Position position : positions) {
            if (!isKillable(piece, position, board))
                unKillablePositions.add(board.getCells()[position.getRow()][position.getCol()].getPosition());
        }
        return unKillablePositions.toArray(new Position[0]);
    }

    private boolean arePiecesOfSamePlayer(Board board, Position origin, Position destination) {
        return board.getPieceAt(origin).getPlayer() == board.getPieceAt(destination).getPlayer();
    }

}
