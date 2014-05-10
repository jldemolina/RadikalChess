package radikalchess.ai;

import radikalchess.model.Board;
import radikalchess.model.Move;
import radikalchess.model.Player;
import radikalchess.model.Position;
import radikalchess.model.checkers.MovementRangeChecker;
import radikalchess.model.pieces.King;
import radikalchess.model.pieces.Pawn;

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
        currentPlayer = (this.playerA.equals(currentPlayer)) ? playerB : playerA;
    }

    public boolean isTerminal() {
        int numberOfKings = 0;
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                if (board.getCells()[i][j].getPiece() instanceof King) numberOfKings++;
            }
        }
        return (numberOfKings != 2);
    }

    public Player getWinner() {
        if (isTerminal())
            return currentPlayer;
        return null;
    }

    public List<Move> getPossibleMovements() {
        Player adversarial = (currentPlayer.equals(playerA)) ? playerB : playerA;
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++)
                if (board.getCells()[i][j].getPiece() != null) {
                    if (board.getCells()[i][j].getPiece().getPlayer().equals(currentPlayer)) {
                        for (Position position : MovementRangeChecker.getInstance().getMovementRangeFor(board.getPieceAt(board.getCells()[i][j].getPosition()), board)) {
                            if (board.getCells()[i][j].getPiece() instanceof King || board.getCells()[i][j].getPiece() instanceof Pawn)
                                moves.add(new Move(board.getCells()[i][j].getPiece().getPosition(), position));
                            else if (isReducedEuclideanDistance(board.getCells()[i][j].getPiece().getPosition(), position, adversarial)
                                    || MovementRangeChecker.getInstance().mayThreatenTheKing(board.getCells()[i][j].getPiece(), position, board)) {
                                moves.add(new Move(board.getCells()[i][j].getPiece().getPosition(), position));
                        }
                    }
                }
            }
        }
        return moves;
    }

    public void move(Move move) {
        board.setPieceAt(move.getDestination(), board.getPieceAt(move.getOrigin()));
            board.setPieceAt(move.getOrigin(), null);
            alternatePlayer();

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