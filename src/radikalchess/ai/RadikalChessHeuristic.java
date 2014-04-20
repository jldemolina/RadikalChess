package radikalchess.ai;

import radikalchess.model.Player;
import radikalchess.model.checkers.PieceAttackRangeChecker;
import radikalchess.model.pieces.Piece;

public class RadikalChessHeuristic implements Heuristic {

    @Override
    public double getValue(RadikalChessStatus status, Player player) {
        double heuristic = 0;
        Player adversarial = (player.equals(status.getPlayerA())) ? status.getPlayerB() : status.getPlayerA();
        for (int i = 0; i < status.getBoard().getNumberOfRows(); i++) {
            for (int j = 0; j < status.getBoard().getNumberOfCols(); j++) {
                if (status.getBoard().getCells()[i][j].getPiece() != null) {
                    if (status.getBoard().getCells()[i][j].getPiece().getPlayer().equals(player)) {
                        for (Piece killablePiece : PieceAttackRangeChecker.getInstance().getKillablePiecesFor(status.getBoard().getCells()[i][j].getPiece(), status.getBoard()))
                            heuristic += killablePiece.getPoints();
                        if (PieceAttackRangeChecker.getInstance().isKillable(status.getBoard().getCells()[i][j].getPiece(), status.getBoard()))
                            heuristic -= status.getBoard().getCells()[i][j].getPiece().getPoints();
                        //heuristic += status.getBoard().getCells()[i][j].getPiece().getPosition().getEuclideanDistanceTo(status.getBoard().searchKingPosition(adversarial));
                    } else {
                        heuristic -= status.getBoard().getCells()[i][j].getPiece().getPoints();
                    }

                }
            }
        }
        return heuristic;
    }

}
