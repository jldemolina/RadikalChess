package radikalchess.ai.heuristics;

import radikalchess.ai.RadikalChessStatus;
import radikalchess.model.Player;

public interface Heuristic {

    public double getValue(RadikalChessStatus status, Player player);

}
