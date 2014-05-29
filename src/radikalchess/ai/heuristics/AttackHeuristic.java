package radikalchess.ai.heuristics;

import radikalchess.ai.RadikalChessStatus;
import radikalchess.model.Player;
import radikalchess.model.checkers.MovementRangeChecker;
import radikalchess.model.pieces.King;
import radikalchess.model.pieces.Piece;

public class AttackHeuristic implements Heuristic {

    @Override
    public double getValue(RadikalChessStatus status, Player player) {
        double heuristic = 0;
        if (loseGame(status, player))
            return Double.NEGATIVE_INFINITY;
        for (int i = 0; i < status.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < status.getBoard().getNumberOfCols(); j++) {
                if (status.getBoard().getCells()[i][j].getPiece() != null) {
                    if (status.getBoard().getCells()[i][j].getPiece().getPlayer().equals(player)) {
                        heuristic += getThreatPoints(status, status.getBoard().getCells()[i][j].getPiece());
                        if (MovementRangeChecker.getInstance().isKillable(status.getBoard().getCells()[i][j].getPiece(), status.getBoard())) {
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

    private double getThreatPoints(RadikalChessStatus status, Piece piece) {
        int threatPoints = 0;
        for (Piece killablePiece : MovementRangeChecker.getInstance().getKillablePiecesFor(piece, status.getBoard()))
            threatPoints += killablePiece.getPoints();
        return threatPoints;
    }

    public boolean loseGame(RadikalChessStatus status, Player player) {
        if (status.isTerminal())
            if (status.getWinner().equals(player))
                return true;
        return false;
    }

    public King getKing(RadikalChessStatus status, Player player) {
        for (int i = 0; i < status.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < status.getBoard().getNumberOfCols(); j++) {
                if (status.getBoard().getCells()[i][j].getPiece() != null)
                    if (status.getBoard().getCells()[i][j].getPiece() instanceof King)
                        if (status.getBoard().getCells()[i][j].getPiece().getPlayer().equals(player))
                            return (King) status.getBoard().getCells()[i][j].getPiece();

            }
        }
        return null;
    }
}

