package view.swing;

import model.Cell;
import model.Pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CellPanel extends JPanel {
    private Cell cell;
    private Color color;
    private JButton pieceButton;
    private Piece piece;
    private boolean pressed;

    public CellPanel(Cell cell, Color color) {
        this.cell = cell;
        this.pieceButton = new JButton();
        this.color = color;
        this.setBackground(color);
        this.setVisible(true);
        preparePieceButton();
    }

    public void addPiece(Piece piece) {
        try {
            cell.setPiece(piece);
            this.piece = piece;
            pieceButton.setIcon(new ImageIcon(ImageIO.read(new ByteArrayInputStream(piece.getImage().getBitmap().getByteArray()))));
        } catch (IOException ex) {
        }
        this.repaint();
    }

    public void removePiece() {
        if (piece != null) {
            cell.setPiece(null);
            piece = null;
            pieceButton.setIcon(null);
            repaint();
        }
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean hasAnyPiece() {
        return piece != null;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void preparePieceButton() {
        pieceButton.setBackground(color);
        pieceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        this.add(pieceButton);
    }

}
