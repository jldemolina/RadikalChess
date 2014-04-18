package radikalchess.ai;

import radikalchess.model.Player;

public interface Heuristic {

    public double getValue(RadikalChessStatus status, Player player);

}
