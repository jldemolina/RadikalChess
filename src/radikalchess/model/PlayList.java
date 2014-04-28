package radikalchess.model;

import java.util.ArrayList;

/**
 * Created by Jose on 27/04/14.
 */
public class PlayList extends ArrayList<Play> {
    private static PlayList instance;

    private PlayList() {
    }

    public static PlayList getInstance() {
        if (instance == null) instance = new PlayList();
        return instance;
    }

}
