package radikalchess.controller;

import radikalchess.model.Board;
import radikalchess.view.swing.ApplicationFrame;

public class SwingApplicationController {

    public void execute() {
        new ApplicationFrame(new Board(6, 4));
    }

}
