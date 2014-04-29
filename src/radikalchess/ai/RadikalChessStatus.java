package radikalchess.ai;

import radikalchess.model.Board;
import radikalchess.model.Move;
import radikalchess.model.Player;
import radikalchess.model.Position;
import radikalchess.model.checkers.MoveChecker;
import radikalchess.model.checkers.MovementRangeChecker;
import radikalchess.model.pieces.King;
import radikalchess.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class RadikalChessStatus {

    private Player playerA;
    private Player playerB;
    private Board board;
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

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void alternatePlayer() {
        currentPlayer = (this.playerA == currentPlayer) ? playerB : playerA;
    }

    public Piece[] getPiecesForPermittedMoves() {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        King threadedKing = threadedKing();
        if (threadedKing == null) {
            for (int i = 0; i < board.getNumberOfRows(); i++) {
                for (int j = 0; j < board.getNumberOfCols(); j++) {
                    if (board.getCells()[i][j].getPiece() != null)
                        if (board.getCells()[i][j].getPiece().getPlayer().equals(currentPlayer))
                            if (!isDefendingTheKing(board.getCells()[i][j].getPiece()))
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
        return (getPossibleMovements().size() == 0);
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
                    for (Position position : MovementRangeChecker.getInstance().getAttackRangeFor(board.getCells()[i][j].getPiece(), board)) {
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
                    for (Position position : MovementRangeChecker.getInstance().getAttackRangeFor(board.getCells()[i][j].getPiece(), board)) {
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
        if (inBoardMove(move = new Move(king.getPosition(), new Position(king.getPosition().getRow() + 1, king.getPosition().getCol())))) {
            if (MoveChecker.getInstance().isAValidKillerMove(move, king, board)) return true;
        }
        if (inBoardMove(move = new Move(king.getPosition(), new Position(king.getPosition().getRow() - 1, king.getPosition().getCol())))) {
            if (MoveChecker.getInstance().isAValidKillerMove(move, king, board)) return true;
        }
        if (inBoardMove(move = new Move(king.getPosition(), new Position(king.getPosition().getRow(), king.getPosition().getCol() + 1)))) {
            if (MoveChecker.getInstance().isAValidKillerMove(move, king, board)) return true;
        }
        if (inBoardMove(move = new Move(king.getPosition(), new Position(king.getPosition().getRow(), king.getPosition().getCol() - 1)))) {
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

    private Piece[] getPiecesCanInterruptCheckMate(King threadedKing, Piece pieceThreading) {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        for (Position position : getPositionsInside(threadedKing.getPosition(), pieceThreading.getPosition())) {
            for (int i = 0; i < board.getNumberOfRows(); i++) {
                for (int j = 0; j < board.getNumberOfCols(); j++) {
                    if (board.getCells()[i][j].getPiece() != null) {
                        if (!(board.getCells()[i][j].getPiece() instanceof King))
                            for (Position killablePosition : MovementRangeChecker.getInstance().getAttackRangeFor(board.getCells()[i][j].getPiece(), board)) {
                                if (killablePosition.equals(position)) {
                                    pieces.add(board.getCells()[i][j].getPiece());
                                }
                            }
                        for (Position killablePosition : MovementRangeChecker.getInstance().getMovementRangeFor(board.getCells()[i][j].getPiece(), board)) {
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
        int distance = (from.getRow() == to.getRow()) ? Math.abs(from.getCol() - to.getCol()) : Math.abs(from.getRow() - to.getRow());
        for (int i = 1; i < distance; i++) {
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
                    for (Piece piece : MovementRangeChecker.getInstance().getKillablePiecesFor(board.getCells()[i][j].getPiece(), board)) {
                        if (piece.equals(pieceToKill)) pieces.add(board.getCells()[i][j].getPiece());
                    }
            }
        }
        return pieces.toArray(new Piece[0]);
    }

    public boolean isDefendingTheKing(Piece piece) {
        boolean defending = false;
        Piece king = threadedKing();
        board.setPieceAt(piece.getPosition(), null);
        if (king != null)
            if (king.getPlayer().equals(piece))
                defending = true;
        board.setPieceAt(piece.getPosition(), piece);
        return defending;
    }

    public List<Move> getPossibleMovements() {
        ArrayList<Move> moves = new ArrayList<Move>();
        King threadedKing = threadedKing();
        if (threadedKing == null)
            moves.addAll(getPossibleMovementsWithoutKingCheck());
        else
            moves.addAll(getPossibleMovementsWithKingCheck(threadedKing));
        return moves;
    }

    public List<Move> getPossibleMovementsWithoutKingCheck() {
        ArrayList<Move> moves = new ArrayList<Move>();
        Piece threadedKing = threadedKing();
        for (Piece piece : getPiecesForPermittedMoves()) {
            for (Position position : MovementRangeChecker.getInstance().getMovementRangeFor(piece, board))
                if (piece instanceof King) {
                    if (MoveChecker.getInstance().isAValidMove(new Move(piece.getPosition(), position), piece, board)
                            || MoveChecker.getInstance().isAValidKillerMove(new Move(piece.getPosition(), position), piece, board)) {
                        moves.add(new Move(piece.getPosition(), position));
                    }
                } else {
                    if (threadedKing == null) {
                        if (isReducedEuclideanDistance(piece.getPosition(), position, (piece.getPlayer().equals(playerA)) ? playerB : playerA)
                                || MovementRangeChecker.getInstance().mayThreatenTheKing(piece, position, board))
                            moves.add(new Move(piece.getPosition(), position));
                    } else {
                        for (Position positionInside : getPositionsInside(piece.getPosition(), threadedKing.getPosition())) {
                            if (positionInside.equals(position))
                                moves.add(new Move(piece.getPosition(), position));
                        }
                    }
                }
        }
        return moves;

    }

    public List<Move> getPossibleMovementsWithKingCheck(King threadedKing) {
        ArrayList<Move> moves = new ArrayList<Move>();
        Piece pieceThreading = getPieceThreadingTheKing(threadedKing);
        Piece[] piecesCanInterruptCheckMate = getPiecesCanInterruptCheckMate(threadedKing, pieceThreading);
        Position[] positionsInside = getPositionsInside(threadedKing.getPosition(), pieceThreading.getPosition());
        Piece[] piecesForPermittedMoved = getPiecesForPermittedMoves();
        for (Piece piece : piecesForPermittedMoved)
            for (Position position : MovementRangeChecker.getInstance().getMovementRangeFor(piece, board))
                if (piece instanceof King) {
                    if (MoveChecker.getInstance().isAValidMove(new Move(piece.getPosition(), position), piece, board)
                            || MoveChecker.getInstance().isAValidKillerMove(new Move(piece.getPosition(), position), piece, board))
                        moves.add(new Move(piece.getPosition(), position));
                } else if (pieceThreading.getPosition().equals(position)) {
                    moves.add(new Move(piece.getPosition(), position));
                } else
                    for (Piece pieceCanInterrupt : piecesCanInterruptCheckMate)
                        if (piece.equals(pieceCanInterrupt))
                            for (Position positionInside : positionsInside)
                                if (position.equals(positionInside))
                                    moves.add(new Move(piece.getPosition(), position));

        return moves;

    }

    public void move(Move move) {
        if (MoveChecker.getInstance().isAValidMove(move, board.getPieceAt(move.getOrigin()), board)) {
            board.setPieceAt(move.getDestination(), board.getPieceAt(move.getOrigin()));
            board.setPieceAt(move.getOrigin(), null);
            alternatePlayer();
        } else if (MoveChecker.getInstance().isAValidKillerMove(move, board.getPieceAt(move.getOrigin()), board)) {
            board.setPieceAt(move.getDestination(), board.getPieceAt(move.getOrigin()));
            board.setPieceAt(move.getOrigin(), null);
            alternatePlayer();
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RadikalChessStatus radikalChessStatus = new RadikalChessStatus((Board) board.clone(), playerA, playerB);
        radikalChessStatus.currentPlayer = currentPlayer;
        return radikalChessStatus;
    }

    private boolean isReducedEuclideanDistance(Position origin, Position destination, Player player) {
        if (board.searchKingPosition(player) != null)
            return new Position(destination.getRow(), destination.getCol()).getEuclideanDistanceTo(board.searchKingPosition(player)) <
                    new Position(origin.getRow(), origin.getCol()).getEuclideanDistanceTo(board.searchKingPosition(player));
        return false;
    }

}