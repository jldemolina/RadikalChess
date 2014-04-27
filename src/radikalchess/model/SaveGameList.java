package radikalchess.model;

import java.util.ArrayList;

/**
 * Created by Jose on 27/04/14.
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
