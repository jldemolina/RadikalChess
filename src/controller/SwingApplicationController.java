package controller;

import model.Board;
import view.swing.ApplicationFrame;

public class SwingApplicationController {

    public void execute() {
        Board board = new Board(6, 4);
        ApplicationFrame applicationFrame = new ApplicationFrame(board);
    }

}
