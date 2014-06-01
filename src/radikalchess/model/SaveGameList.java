package radikalchess.model;

import java.util.ArrayList;

/**
 * Singleton class that represents the list of saved games
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 */
public class SaveGameList extends ArrayList<SaveGame> {
    private static SaveGameList instance;

    private SaveGameList() {
    }

    public static SaveGameList getInstance() {
        if (instance == null) instance = new SaveGameList();
        return instance;
    }
}
