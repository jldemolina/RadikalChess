package radikalchess.model;

import java.util.ArrayList;

public class SaveGameList extends ArrayList<SaveGame> {
    private static SaveGameList instance;

    private SaveGameList() {
    }

    public static SaveGameList getInstance() {
        if (instance == null) instance = new SaveGameList();
        return instance;
    }
}
