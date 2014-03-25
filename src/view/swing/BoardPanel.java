package view.swing;

import model.Board;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private CellPanel[][] cellPanels;
    private Board board;
    private boolean movement;

    public BoardPanel(Board board) {
        this.movement = false;
        cellPanels = new CellPanel[board.getNumberOfRows()][board.getNumberOfCols()];
        this.setLayout(new GridLayout(board.getNumberOfRows(), board.getNumberOfCols()));
        this.board = board;
        fillCellPanels();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
    }


    private boolean isMoving() {
        return movement;
    }

    private void fillCellPanels() {
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                if ((j + i) % 2 == 0) {
                    cellPanels[i][j] = new CellPanel(i, j, Color.BLACK);
                } else {
                    cellPanels[i][j] = new CellPanel(i, j, Color.WHITE);
                }
                this.add(cellPanels[i][j]);
                cellPanels[i][j].setButton(new JButton(""));
            }
        }
    }
}
