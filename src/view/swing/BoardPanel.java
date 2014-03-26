package view.swing;

import model.Bitmap;
import model.Board;
import model.Image;
import model.Pieces.Pawn;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    private CellPanel[][] cellPanels;
    private Board board;
    private boolean movement;

    public BoardPanel(Board board) {
        this.board = board;
        this.movement = false;
        cellPanels = new CellPanel[board.getNumberOfRows()][board.getNumberOfCols()];
        this.setLayout(new GridLayout(board.getNumberOfRows(), board.getNumberOfCols()));
        this.setVisible(true);
        initializeBoard();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
    }

    private boolean isMoving() {
        return movement;
    }

    private void initializeBoard() {
        fillCellPanels();
        placePieces();
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
            }
        }
    }

    private void placePieces() {
        cellPanels[0][0].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/blueking.png"))));
        cellPanels[0][1].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/bluequeen.png"))));
        cellPanels[0][2].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/bluebishop.png"))));
        cellPanels[0][3].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/bluerook.png"))));

        cellPanels[1][0].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/bluepawn.png"))));
        cellPanels[1][1].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/bluepawn.png"))));
        cellPanels[1][2].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/bluepawn.png"))));
        cellPanels[1][3].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/bluepawn.png"))));

        cellPanels[4][0].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/lilacpawn.png"))));
        cellPanels[4][1].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/lilacpawn.png"))));
        cellPanels[4][2].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/lilacpawn.png"))));
        cellPanels[4][3].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/lilacpawn.png"))));

        cellPanels[5][0].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/lilacrook.png"))));
        cellPanels[5][1].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/lilacbishop.png"))));
        cellPanels[5][2].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/lilacqueen.png"))));
        cellPanels[5][3].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/lilacking.png"))));
        cellPanels[5][3].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/lilacking.png"))));
    }

}
