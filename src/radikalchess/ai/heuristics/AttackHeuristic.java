package radikalchess.ai.heuristics;

import radikalchess.ai.RadikalChessStatus;
import radikalchess.model.Player;
import radikalchess.model.checkers.MovementRangeChecker;
import radikalchess.model.pieces.Piece;

public class AttackHeuristic implements Heuristic {

    @Override
    public double getValue(RadikalChessStatus status, Player player) {
        double heuristic = 0;
        for (int i = 0; i < status.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < status.getBoard().getNumberOfCols(); j++) {
                if (status.getBoard().getCells()[i][j].getPiece() != null) {
                    if (status.getBoard().getCells()[i][j].getPiece().getPlayer().equals(player)) {
                        heuristic += status.getBoard().getCells()[i][j].getPiece().getPoints();
                        for (Piece killablePiece : MovementRangeChecker.getInstance().getKillablePiecesFor(status.getBoard().getCells()[i][j].getPiece(), status.getBoard()))
                            heuristic += killablePiece.getPoints() / 2;
                    } else {
                        heuristic -= status.getBoard().getCells()[i][j].getPiece().getPoints() * 4;
                    }

                }
            }
        }
        return heuristic;
    }

}
