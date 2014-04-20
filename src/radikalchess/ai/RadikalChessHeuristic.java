package radikalchess.ai;

import radikalchess.model.Player;
import radikalchess.model.checkers.PieceAttackRangeChecker;
import radikalchess.model.pieces.King;
import radikalchess.model.pieces.Piece;

public class RadikalChessHeuristic implements Heuristic {

    @Override
    public double getValue(RadikalChessStatus status, Player player) {
        double heuristic = 0;
        for (int i = 0; i < status.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < status.getBoard().getNumberOfCols(); j++) {
                if (status.getBoard().getCells()[i][j].getPiece() != null) {
                    if (status.getBoard().getCells()[i][j].getPiece().getPlayer().equals(player)) {
                        heuristic += status.getBoard().getCells()[i][j].getPiece().getPoints();
                        for (Piece killablePiece : PieceAttackRangeChecker.getInstance().getKillablePiecesFor(status.getBoard().getCells()[i][j].getPiece(), status.getBoard()))
                            heuristic += killablePiece.getPoints() / 2;
                        if (PieceAttackRangeChecker.getInstance().isKillable(status.getBoard().getCells()[i][j].getPiece(), status.getBoard())) {
                            heuristic -= status.getBoard().getCells()[i][j].getPiece().getPoints() * 2;
                            if ((status.getBoard().getCells()[i][j].getPiece() instanceof King)) {
                                heuristic -= Double.NEGATIVE_INFINITY;
                            }
                        }
                    } else {
                        heuristic -= status.getBoard().getCells()[i][j].getPiece().getPoints() * 2;
                    }

                }
            }
        }
        return heuristic;
    }

}
