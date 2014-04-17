package radikalchess.controller;

import radikalchess.ai.RadikalChessStatus;
import radikalchess.model.Board;
import radikalchess.model.Player;
import radikalchess.view.swing.ApplicationFrame;

/**
 * This class is responsible for the total control of the application. It connects the model and the view (and, of course, the IA)
 *
 * @author Jose Luis Molina
 */
public class SwingApplicationController {

    public void execute() {
        new ApplicationFrame(new RadikalChessStatus(new Board(6, 4), new Player("White player"), new Player("Black player")));
    }

}
