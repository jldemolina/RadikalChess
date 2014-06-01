package radikalchess.ai.heuristics;

import radikalchess.ai.RadikalChessStatus;
import radikalchess.model.Player;
import radikalchess.model.checkers.MovementRangeChecker;
import radikalchess.model.pieces.King;
import radikalchess.model.pieces.Piece;

/**
 * Heuristics attack, attack centered rather than self-defense. It could be considered of medium difficulty.
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 */
public class MediumHeuristic implements Heuristic {

    /**
     * Gets the heuristic value
     *
     * @param status The current game status
     * @param player The current player
     */
    @Override
    public double getValue(RadikalChessStatus status, Player player) {
        double heuristic = 0;
        if (loseGame(status, player))
            return Double.NEGATIVE_INFINITY;
        for (int i = 0; i < status.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < status.getBoard().getNumberOfCols(); j++) {
                if (status.getBoard().getCells()[i][j].getPiece() != null) {
                    if (status.getBoard().getCells()[i][j].getPiece().getPlayer().equals(player)) {
                        heuristic += status.getBoard().getCells()[i][j].getPiece().getPoints();
                        heuristic += getTheBiggestThreatPoints(status, status.getBoard().getCells()[i][j].getPiece());
                        if (MovementRangeChecker.getInstance().isKillable(status.getBoard().getCells()[i][j].getPiece(), status.getBoard())) {
                            heuristic -= status.getBoard().getCells()[i][j].getPiece().getPoints();
                            if (status.getBoard().getCells()[i][j].getPiece() instanceof King) {
                                return Double.NEGATIVE_INFINITY;
                            }
                        }
                    } else {
                        heuristic -= status.getBoard().getCells()[i][j].getPiece().getPoints();
                    }
                }

            }
        }
        return heuristic;
    }

    /**
     * Gets the value of the greater part being threatened by a particular piece
     *
     * @param status The current game status
     * @param piece  The piece from which you want to get the sum of the pieces threatened
     */
    private double getTheBiggestThreatPoints(RadikalChessStatus status, Piece piece) {
        int biggestPiecePoints = 0;
        for (Piece killablePiece : MovementRangeChecker.getInstance().getKillablePiecesFor(piece, status.getBoard()))
            if (killablePiece.getPoints() > biggestPiecePoints) biggestPiecePoints = killablePiece.getPoints();
        return biggestPiecePoints;
    }

    /**
     * Check if the game is lost in this state
     *
     * @param status The current game status
     * @param player The current player
     */
    public boolean loseGame(RadikalChessStatus status, Player player) {
        if (status.isTerminal())
            if (status.getWinner().equals(player))
                return true;
        return false;
    }

}
