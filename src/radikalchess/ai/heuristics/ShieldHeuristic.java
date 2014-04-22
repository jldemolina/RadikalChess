package radikalchess.ai.heuristics;

import radikalchess.ai.RadikalChessStatus;
import radikalchess.model.Player;
import radikalchess.model.checkers.PieceAttackRangeChecker;
import radikalchess.model.pieces.King;

public class ShieldHeuristic implements Heuristic {

    @Override
    public double getValue(RadikalChessStatus status, Player player) {
        double heuristic = 0;
        for (int i = 0; i < status.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < status.getBoard().getNumberOfCols(); j++) {
                if (status.getBoard().getCells()[i][j].getPiece() != null) {
                    if (status.getBoard().getCells()[i][j].getPiece().getPlayer().equals(player)) {
                        if (PieceAttackRangeChecker.getInstance().isKillable(status.getBoard().getCells()[i][j].getPiece(), status.getBoard())) {
                            heuristic -= status.getBoard().getCells()[i][j].getPiece().getPoints() * 3;
                            if ((status.getBoard().getCells()[i][j].getPiece() instanceof King)) {
                                heuristic -= Double.NEGATIVE_INFINITY;
                            }
                        }
                    } else {
                        heuristic -= status.getBoard().getCells()[i][j].getPiece().getPoints() * 4;
                    }
                }
            }
        }
        return heuristic;
    }

}
