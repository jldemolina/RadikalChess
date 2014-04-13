package radikalchess.model.checkers;

import radikalchess.model.Board;
import radikalchess.model.Move;
import radikalchess.model.Position;
import radikalchess.model.pieces.*;

/**
 * TThis is a special kind of movement verification (respects the Singleton pattern). Lets you know if a piece
 * can be moved to another position on the board, knowing the rules of the game.
 *
 * @author Jose Luis Molina
 */
public class MoveChecker {
    public static MoveChecker INSTANCE;

    private MoveChecker() {
    }

    public static MoveChecker getInstance() {
        if (INSTANCE == null) INSTANCE = new MoveChecker();
        return INSTANCE;
    }

    public boolean isAValidMove(Move move, Piece piece, Board board) {
        if (piece instanceof Pawn) return isAValidPawnMove(move);
        if (piece instanceof Bishop) return isAValidBishopMove(move, board);
        if (piece instanceof Rook) return isAValidRookMove(move, board);
        if (piece instanceof Queen) return isAValidQueenMove(move, board);
        if (piece instanceof King) return isAValidKingMove((King) piece, move, board);
        return false;
    }

    public boolean isAValidKillerMove(Move move, Piece piece, Board board) {
        if (arePiecesOfSamePlayer(board, move.getOrigin(), move.getDestination())) return false;
        if (piece instanceof Pawn) return isAValidKillerPawnMove(move);
        if (piece instanceof Bishop) return isAValidKillerBishopMove(move, board);
        if (piece instanceof Rook) return isAValidKillerRookMove(move, board);
        if (piece instanceof Queen) return isAValidKillerQueenMove(move, board);
        if (piece instanceof King) return isAValidKingMove((King) piece, move, board);
        return false;
    }

    private boolean isAValidPawnMove(Move move) {
        return ((Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == 0 && Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) == 1));
    }

    private boolean isAValidBishopMove(Move move, Board board) {
        return isAValidCompleteDiagonalMove(move, board);
    }

    private boolean isAValidRookMove(Move move, Board board) {
        return (isAValidCompleteHorizontalMove(move, board) || isAValidCompleteVerticalMove(move, board));
    }

    private boolean isAValidQueenMove(Move move, Board board) {
        return (isAValidCompleteHorizontalMove(move, board) || isAValidCompleteVerticalMove(move, board) || isAValidCompleteDiagonalMove(move, board));
    }

    private boolean isAValidKingMove(King king, Move move, Board board) {
        if (isAValidPawnMove(move) || (Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == 1 && Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) == 1)) {
            return !PieceAttackRangeChecker.getInstance().isKillable(king, move.getDestination(), board);
        }
        return false;
    }

    private boolean isAValidCompleteHorizontalMove(Move move, Board board) {
        if (move.getOrigin().getCol() - move.getDestination().getCol() > 0 && (move.getOrigin().getRow() - move.getDestination().getRow()) == 0) {
            for (int i = move.getOrigin().getCol() - 1; i >= move.getDestination().getCol(); i--) {
                if (board.getCells()[move.getDestination().getRow()][i].getPiece() != null) return false;
            }
            return true;
        } else if (move.getOrigin().getCol() - move.getDestination().getCol() < 0 && (move.getOrigin().getRow() - move.getDestination().getRow()) == 0) {
            for (int i = move.getOrigin().getCol() + 1; i < move.getDestination().getCol(); i++) {
                if (board.getCells()[move.getDestination().getRow()][i].getPiece() != null) return false;
            }
            return true;
        }
        return false;
    }

    private boolean isAValidCompleteVerticalMove(Move move, Board board) {
        if (move.getOrigin().getCol() - move.getDestination().getCol() == 0 && (move.getOrigin().getRow() - move.getDestination().getRow()) > 0) {
            for (int i = move.getOrigin().getRow() - 1; i >= move.getDestination().getRow(); i--) {
                if (board.getCells()[i][move.getDestination().getCol()].getPiece() != null) return false;
            }
            return true;
        } else if (move.getOrigin().getCol() - move.getDestination().getCol() == 0 && (move.getOrigin().getRow() - move.getDestination().getRow()) < 0) {
            for (int i = move.getOrigin().getRow() + 1; i < move.getDestination().getRow(); i++) {
                if (board.getCells()[i][move.getDestination().getCol()].getPiece() != null) return false;
            }
            return true;
        }
        return false;
    }

    private boolean isAValidCompleteDiagonalMove(Move move, Board board) {
        return (isAValidDownwardCompleteDiagonalMove(move, board) || isAValidUpwardCompleteDiagonalMove(move, board));
    }

    private boolean isAValidDownwardCompleteDiagonalMove(Move move, Board board) {
        if (Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) &&
                move.getOrigin().getRow() < move.getDestination().getRow() && move.getOrigin().getCol() < move.getDestination().getCol()) {
            for (int i = 1; i <= Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()); i++) {
                if (board.getCells()[move.getOrigin().getRow() + i][move.getOrigin().getCol() + i].getPiece() != null)
                    return false;
            }
            return true;
        } else if (Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) &&
                move.getOrigin().getRow() < move.getDestination().getRow() && move.getOrigin().getCol() > move.getDestination().getCol()) {
            for (int i = 1; i <= Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()); i++) {
                if (board.getCells()[move.getOrigin().getRow() + i][move.getOrigin().getCol() - i].getPiece() != null)
                    return false;
            }
            return true;
        }
        return false;
    }

    private boolean isAValidUpwardCompleteDiagonalMove(Move move, Board board) {
        if (Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) &&
                move.getOrigin().getRow() > move.getDestination().getRow() && move.getOrigin().getCol() < move.getDestination().getCol()) {
            for (int i = 1; i <= Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()); i++) {
                if (board.getCells()[move.getOrigin().getRow() - i][move.getOrigin().getCol() + i].getPiece() != null)
                    return false;
            }
            return true;
        } else if (Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) &&
                move.getOrigin().getRow() > move.getDestination().getRow() && move.getOrigin().getCol() > move.getDestination().getCol()) {
            for (int i = 1; i <= Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()); i++) {
                if (board.getCells()[move.getOrigin().getRow() - i][move.getOrigin().getCol() - i].getPiece() != null)
                    return false;
            }
            return true;
        }
        return false;
    }

    private boolean isAValidKillerPawnMove(Move move) {
        return (Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == 1 && Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) == 1);
    }

    private boolean isAValidKillerQueenMove(Move move, Board board) {
        return (isAValidCompleteKillerHorizontalMove(move, board) || isAValidCompleteKillerVerticalMove(move, board) || isAValidCompleteKillerDiagonalMove(move, board));
    }

    private boolean isAValidKillerRookMove(Move move, Board board) {
        return (isAValidCompleteKillerHorizontalMove(move, board) || isAValidCompleteKillerVerticalMove(move, board));
    }

    private boolean isAValidKillerBishopMove(Move move, Board board) {
        return isAValidCompleteKillerDiagonalMove(move, board);
    }

    private boolean isAValidCompleteKillerHorizontalMove(Move move, Board board) {
        if (move.getOrigin().getCol() - move.getDestination().getCol() > 0 && (move.getOrigin().getRow() - move.getDestination().getRow()) == 0) {
            for (int i = move.getOrigin().getCol() - 1; i >= move.getDestination().getCol() + 1; i--) {
                if (board.getCells()[move.getDestination().getRow()][i].getPiece() != null) return false;
            }
            return true;
        } else if (move.getOrigin().getCol() - move.getDestination().getCol() < 0 && (move.getOrigin().getRow() - move.getDestination().getRow()) == 0) {
            for (int i = move.getOrigin().getCol() + 1; i < move.getDestination().getCol() - 1; i++) {
                if (board.getCells()[move.getDestination().getRow()][i].getPiece() != null) return false;
            }
            return true;
        }
        return false;
    }

    private boolean isAValidCompleteKillerVerticalMove(Move move, Board board) {
        if (move.getOrigin().getCol() - move.getDestination().getCol() == 0 && (move.getOrigin().getRow() - move.getDestination().getRow()) > 0) {
            for (int i = move.getOrigin().getRow() - 1; i >= move.getDestination().getRow() + 1; i--) {
                if (board.getCells()[i][move.getDestination().getCol()].getPiece() != null) return false;
            }
            return true;
        } else if (move.getOrigin().getCol() - move.getDestination().getCol() == 0 && (move.getOrigin().getRow() - move.getDestination().getRow()) < 0) {
            for (int i = move.getOrigin().getRow() + 1; i < move.getDestination().getRow() - 1; i++) {
                if (board.getCells()[i][move.getDestination().getCol()].getPiece() != null) return false;
            }
            return true;
        }
        return false;
    }


    private boolean isAValidCompleteKillerDiagonalMove(Move move, Board board) {
        return (isAValidDownwardCompleteKillerDiagonalMove(move, board) || isAValidUpwardCompleteKillerDiagonalMove(move, board));
    }

    private boolean isAValidDownwardCompleteKillerDiagonalMove(Move move, Board board) {
        if (Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) &&
                move.getOrigin().getRow() < move.getDestination().getRow() && move.getOrigin().getCol() < move.getDestination().getCol()) {
            for (int i = 1; i <= Math.abs(move.getOrigin().getCol() - move.getDestination().getCol() + 1); i++) {
                if (board.getCells()[move.getOrigin().getRow() + i][move.getOrigin().getCol() + i].getPiece() != null)
                    return false;
            }
            return true;
        } else if (Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) &&
                move.getOrigin().getRow() < move.getDestination().getRow() && move.getOrigin().getCol() > move.getDestination().getCol()) {
            for (int i = 1; i <= Math.abs(move.getOrigin().getCol() - move.getDestination().getCol() - 1); i++) {
                if (board.getCells()[move.getOrigin().getRow() + i][move.getOrigin().getCol() - i].getPiece() != null)
                    return false;
            }
            return true;
        }
        return false;
    }

    private boolean isAValidUpwardCompleteKillerDiagonalMove(Move move, Board board) {
        if (Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) &&
                move.getOrigin().getRow() > move.getDestination().getRow() && move.getOrigin().getCol() < move.getDestination().getCol()) {
            for (int i = 1; i <= Math.abs(move.getOrigin().getCol() - move.getDestination().getCol() + 1); i++) {
                if (board.getCells()[move.getOrigin().getRow() - i][move.getOrigin().getCol() + i].getPiece() != null)
                    return false;
            }
            return true;
        } else if (Math.abs(move.getOrigin().getCol() - move.getDestination().getCol()) == Math.abs(move.getOrigin().getRow() - move.getDestination().getRow()) &&
                move.getOrigin().getRow() > move.getDestination().getRow() && move.getOrigin().getCol() > move.getDestination().getCol()) {
            for (int i = 1; i <= Math.abs(move.getOrigin().getCol() - move.getDestination().getCol() - 1); i++) {
                if (board.getCells()[move.getOrigin().getRow() - i][move.getOrigin().getCol() - i].getPiece() != null)
                    return false;
            }
            return true;
        }
        return false;
    }

    private boolean arePiecesOfSamePlayer(Board board, Position origin, Position destination) {
        return board.getPieceAt(origin).getPlayer() == board.getPieceAt(destination).getPlayer();
    }
}
