package radikalchess.controller;

import radikalchess.model.Board;
import radikalchess.model.Player;
import radikalchess.view.swing.ApplicationFrame;

/**
 * This class is responsible for the total control of the application.
 *
 * @author Jose Luis Molina
 */
public class SwingApplicationController {

    public void execute() {
        new ApplicationFrame(new Player("White player"), new Player("Black player"), new Board(6, 4));
    }

}
