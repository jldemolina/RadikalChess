package radikalchess.controller;

import radikalchess.model.Board;
import radikalchess.model.Player;
import radikalchess.view.swing.ApplicationFrame;

public class SwingApplicationController {

    public void execute() {
        new ApplicationFrame(new Player("White player"), new Player("Black player"), new Board(6, 4));
    }

}
