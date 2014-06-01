package radikalchess.model;

import radikalchess.ai.RadikalChessGame;

import java.util.Date;

/**
 * Represents a saved game. A saved game is made by a state game and a specific date.
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 */
public class SaveGame {
    private final RadikalChessGame game;
    private final Date date;

    public SaveGame(RadikalChessGame game, Date date) {
        this.game = game;
        this.date = date;
    }

    public RadikalChessGame getGame() {
        return game;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return date.getDay() + "/" + date.getMonth() + "/" + date.getYear() + " at " + date.getHours() + ":" + date.getMinutes();
    }
}
