package view.swing;

import model.*;
import model.Image;
import model.Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardPanel extends JPanel {

    private CellPanel[][] cellPanels;
    private Board board;

    public BoardPanel(Board board) {
        this.board = board;
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

    private void initializeBoard() {
        fillCellPanels();
        placePieces();
    }

    private void fillCellPanels() {
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                if ((j + i) % 2 == 0) {
                    cellPanels[i][j] = new CellPanel(board.getCells()[i][j], Color.BLACK);
                } else {
                    cellPanels[i][j] = new CellPanel(board.getCells()[i][j], Color.WHITE);
                }
                addCellPanelActionListener(cellPanels[i][j]);
                this.add(cellPanels[i][j]);
            }
        }
    }

    private void addCellPanelActionListener(final CellPanel cellPanel) {
        cellPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!cellPanel.hasAnyPiece()) {
                    movePressedPieceTo(cellPanel);
                } else {
                    unPressedCellsWithPieces();
                    cellPanel.setPressed(true);
                }
            }
        });
    }

    private void movePressedPieceTo(CellPanel cellPanel) {
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                if (cellPanels[i][j].hasAnyPiece()) {
                    if (cellPanels[i][j].isPressed()) {
                        if (MoveChecker.getInstance().isAValidMove(new Move
                                (new Position(i, j), new Position(cellPanel.getPosition().getRow(), cellPanel.getPosition().getCol())),
                                cellPanels[i][j].getPiece(), board)) {
                            cellPanel.addPiece(cellPanels[i][j].getPiece());
                            cellPanels[i][j].removePiece();
                            cellPanels[i][j].setPressed(false);
                            cellPanel.setPressed(false);
                        }
                        break;
                    }
                }
            }
        }
    }

    private void unPressedCellsWithPieces() {
        for (int i = 0; i < board.getNumberOfRows(); i++) {
            for (int j = 0; j < board.getNumberOfCols(); j++) {
                if (cellPanels[i][j].hasAnyPiece()) {
                    if (cellPanels[i][j].isPressed()) {
                        cellPanels[i][j].setPressed(false);
                    }
                }
            }
        }
    }

    private void placePieces() {
        cellPanels[0][0].addPiece(new King(0, new Image(new Bitmap("images/pieces/blueking.png"))));
        cellPanels[0][1].addPiece(new Queen(0, new Image(new Bitmap("images/pieces/bluequeen.png"))));
        cellPanels[0][2].addPiece(new Bishop(0, new Image(new Bitmap("images/pieces/bluebishop.png"))));
        cellPanels[0][3].addPiece(new Rook(0, new Image(new Bitmap("images/pieces/bluerook.png"))));

        cellPanels[1][0].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/bluepawn.png"))));
        cellPanels[1][1].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/bluepawn.png"))));
        cellPanels[1][2].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/bluepawn.png"))));
        cellPanels[1][3].addPiece(new Pawn(0, new Image(new Bitmap("images/pieces/bluepawn.png"))));

        cellPanels[4][0].addPiece(new Pawn(1, new Image(new Bitmap("images/pieces/lilacpawn.png"))));
        cellPanels[4][1].addPiece(new Pawn(1, new Image(new Bitmap("images/pieces/lilacpawn.png"))));
        cellPanels[4][2].addPiece(new Pawn(1, new Image(new Bitmap("images/pieces/lilacpawn.png"))));
        cellPanels[4][3].addPiece(new Pawn(1, new Image(new Bitmap("images/pieces/lilacpawn.png"))));

        cellPanels[5][0].addPiece(new Rook(1, new Image(new Bitmap("images/pieces/lilacrook.png"))));
        cellPanels[5][1].addPiece(new Bishop(1, new Image(new Bitmap("images/pieces/lilacbishop.png"))));
        cellPanels[5][2].addPiece(new Queen(1, new Image(new Bitmap("images/pieces/lilacqueen.png"))));
        cellPanels[5][3].addPiece(new King(1, new Image(new Bitmap("images/pieces/lilacking.png"))));
    }

}
