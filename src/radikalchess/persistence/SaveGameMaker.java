package radikalchess.persistence;

import radikalchess.ai.RadikalChessGame;

/**
 * Is responsible for creating a new saved game and store it in a specific place
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 * @see radikalchess.model.SaveGame
 */
public interface SaveGameMaker {

    public void save(RadikalChessGame game);

}
