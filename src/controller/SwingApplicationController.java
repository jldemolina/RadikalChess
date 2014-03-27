package controller;

import model.Board;
import view.swing.ApplicationFrame;

public class SwingApplicationController {

    public void execute() {
        new ApplicationFrame(new Board(6, 4));
    }

}
