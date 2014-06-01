package radikalchess.persistence;

/**
 * Load the list of saved games in the Singleton class SaveGameList
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 * @see radikalchess.model.SaveGame
 */
public interface SaveGameListLoader {

    public void load();

}
