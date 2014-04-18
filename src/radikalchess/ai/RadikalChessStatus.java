package radikalchess.ai;

import radikalchess.model.Board;
import radikalchess.model.Move;
import radikalchess.model.Player;
import radikalchess.model.Position;
import radikalchess.model.checkers.MoveChecker;
import radikalchess.model.checkers.PieceAttackRangeChecker;
import radikalchess.model.pieces.King;
import radikalchess.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class RadikalChessStatus {

    private Board board;
    private final Player playerA;
    private final Player playerB;
    private Player currentPlayer;

    public RadikalChessStatus(Board board, Player playerA, Player playerB) {
        this.board = board;
        this.playerA = this.currentPlayer = playerA;
        this.playerB = playerB;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayerA() {
        return playerA;
    }

    public Player getPlayerB() {
        return playerB;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void alternatePlayer() {
        currentPlayer = (this.playerA == currentPlayer) ? playerB : playerA;
    }

    /**
     * TODO
     *
     * @return
     */
    public Piece[] getPiecesForPermittedMoves() {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        King threadedKing = threadedKing();
        if (threadedKing == null) {
            for (int i = 0; i < board.getNumberOfRows(); i++) {
                for (int j = 0; j < board.getNumberOfCols(); j++) {
                    if (board.getCells()[i][j].getPiece() != null)
                        pieces.add(board.getCells()[i][j].getPiece());
                }
            }
        } else {
            if (canBeMoved(threadedKing)) pieces.add(threadedKing);
            for (Piece piece : getPiecesCanInterruptCheckMate(threadedKing, getPieceThreadingTheKing(threadedKing)))
                if (piece.getPlayer().equals(threadedKing.getPlayer())) pieces.add(piece);
            for (Piece piece : getPiecesCanKill(getPieceThreadingTheKing(threadedKing)))
                pieces.add(piece);
        }
        return pieces.toArray(new Piece[0]);
    }

    public boolean isTerminal() {
        King threadedKing = threadedKing();
        if (threadedKing != null) {
            Piece pieceThreading = getPieceThreadingTheKing(threadedKing);
            if (PieceAttackRangeChecker.getInstance().isKillable(pieceThreading, board)) return false;
            return !checkMateCanBeInterrupted(threadedKing, pieceThreading) && !canBeMoved(threadedKing);
        }
        return false;
    }

    public Player getWinner() {
        if (isTerminal())
            return currentPlayer;
        return null;
    }

    public King threadedKing() {
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                if (board.getCells()[i][j].getPiece() != null) {
                    for (Position position : PieceAttackRangeChecker.getInstance().getAttackRangeFor(board.getCells()[i][j].getPiece(), board)) {
                        if (board.getPieceAt(position) instanceof King) {
                            return (King) board.getPieceAt(position);
                        }
                    }
                }
            }
        }
        return null;
    }

    public Piece getPieceThreadingTheKing(King king) {
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                if (board.getCells()[i][j].getPiece() != null) {
                    for (Position position : PieceAttackRangeChecker.getInstance().getAttackRangeFor(board.getCells()[i][j].getPiece(), board)) {
                        if (board.getPieceAt(position) != null) {
                            if (board.getPieceAt(position).equals(king)) {
                                return board.getCells()[i][j].getPiece();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private boolean canBeMoved(King king) {
        Move move;
        if (inBoardMove(move = new Move(king.getPosition(), new Position(king.getPosition().getRow() + 1, king.getPosition().getCol() + 1)))) {
            if (MoveChecker.getInstance().isAValidKillerMove(move, king, board)) return true;
        }
        if (inBoardMove(move = new Move(king.getPosition(), new Position(king.getPosition().getRow() + 1, king.getPosition().getCol() - 1)))) {
            if (MoveChecker.getInstance().isAValidKillerMove(move, king, board)) return true;
        }
        if (inBoardMove(move = new Move(king.getPosition(), new Position(king.getPosition().getRow() - 1, king.getPosition().getCol() + 1)))) {
            if (MoveChecker.getInstance().isAValidKillerMove(move, king, board)) return true;
        }
        if (inBoardMove(move = new Move(king.getPosition(), new Position(king.getPosition().getRow() - 1, king.getPosition().getCol() - 1)))) {
            if (MoveChecker.getInstance().isAValidKillerMove(move, king, board)) return true;
        }
        return false;
    }

    private boolean inBoardMove(Move move) {
        return move.getOrigin().getCol() >= 0
                && move.getOrigin().getCol() < board.getNumberOfCols()
                && move.getDestination().getCol() >= 0
                && move.getDestination().getCol() < board.getNumberOfCols()
                && move.getOrigin().getRow() >= 0
                && move.getOrigin().getRow() < board.getNumberOfRows()
                && move.getDestination().getRow() >= 0
                && move.getDestination().getRow() < board.getNumberOfRows();
    }

    private boolean checkMateCanBeInterrupted(King threadedKing, Piece pieceThreading) {
        for (Position position : getPositionsInside(threadedKing.getPosition(), pieceThreading.getPosition())) {
            for (int i = 0; i < board.getNumberOfRows(); i++) {
                for (int j = 0; j < board.getNumberOfCols(); j++) {
                    if (board.getCells()[i][j].getPiece() != null) {
                        for (Position killablePosition : PieceAttackRangeChecker.getInstance().getAttackRangeFor(board.getCells()[i][j].getPiece(), board)) {
                            if (killablePosition.equals(position)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private Piece[] getPiecesCanInterruptCheckMate(King threadedKing, Piece pieceThreading) {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (Position position : getPositionsInside(threadedKing.getPosition(), pieceThreading.getPosition())) {
            for (int i = 0; i < board.getNumberOfRows(); i++) {
                for (int j = 0; j < board.getNumberOfCols(); j++) {
                    if (board.getCells()[i][j].getPiece() != null) {
                        for (Position killablePosition : PieceAttackRangeChecker.getInstance().getAttackRangeFor(board.getCells()[i][j].getPiece(), board)) {
                            if (killablePosition.equals(position)) {
                                pieces.add(board.getCells()[i][j].getPiece());
                            }
                        }
                    }
                }
            }
        }
        return pieces.toArray(new Piece[0]);
    }

    private Position[] getPositionsInside(Position from, Position to) {
        ArrayList<Position> positions = new ArrayList<Position>();

        for (int i = 1; i < Math.abs(from.getRow() - to.getRow()); i++) {
            if (from.getRow() < to.getRow()) {
                if (from.getCol() < to.getCol()) positions.add(new Position(from.getRow() + i, from.getCol() + i));
                else if (from.getCol() > to.getCol()) positions.add(new Position(from.getRow() + i, from.getCol() - i));
                else positions.add(new Position(from.getRow() + i, from.getCol()));
            } else if (from.getRow() > to.getRow()) {
                if (from.getCol() < to.getCol()) positions.add(new Position(from.getRow() - i, from.getCol() + i));
                else if (from.getCol() > to.getCol()) positions.add(new Position(from.getRow() - i, from.getCol() - i));
                else positions.add(new Position(from.getRow() - i, from.getCol()));
            } else {
                if (from.getCol() < to.getCol()) positions.add(new Position(from.getRow(), from.getCol() + i));
                else if (from.getCol() > to.getCol()) positions.add(new Position(from.getRow(), from.getCol() - i));
                else positions.add(new Position(from.getRow(), from.getCol()));
            }
        }

        return positions.toArray(new Position[0]);
    }

    private Piece[] getPiecesCanKill(Piece pieceToKill) {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                if (board.getCells()[i][j].getPiece() != null)
                    for (Piece piece : PieceAttackRangeChecker.getInstance().getKillablePiecesFor(board.getCells()[i][j].getPiece(), board)) {
                        if (piece.equals(pieceToKill)) pieces.add(board.getCells()[i][j].getPiece());
                    }
            }
        }
        return pieces.toArray(new Piece[0]);
    }

    public boolean isDefendingTheKing(Piece piece) {
        boolean defending = false;
        board.setPieceAt(piece.getPosition(), null);
        if (threadedKing() != null) defending = true;
        board.setPieceAt(piece.getPosition(), piece);
        return defending;
    }

    /**
     * TODO
     */
    public List<Move> getPossibleMovements() {
        ArrayList<Move> moves = new ArrayList<Move>();
        for (Piece piece : getPiecesForPermittedMoves()) {
            for (Position position : PieceAttackRangeChecker.getInstance().getMovementRangeFor(piece, board))
                moves.add(new Move(piece.getPosition(), position));
        }
        return moves;
    }

    /**
     * TODO
     */
    public void move(Move move) {
        if (MoveChecker.getInstance().isAValidMove(move, board.getPieceAt(move.getOrigin()), board)
                || MoveChecker.getInstance().isAValidKillerMove(move, board.getPieceAt(move.getOrigin()), board)) {
            board.setPieceAt(move.getDestination(), board.getPieceAt(move.getOrigin()));
            board.setPieceAt(move.getOrigin(), null);
            alternatePlayer();
        }
    }

    @Override
    public Object clone() {
        return new RadikalChessStatus(board, playerA, playerB);
    }

}