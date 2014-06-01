package radikalchess.ai.heuristics;

import radikalchess.ai.RadikalChessStatus;
import radikalchess.model.Player;

/**
 * Interface representing heuristics
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 */
public interface Heuristic {

    public double getValue(RadikalChessStatus status, Player player);

}