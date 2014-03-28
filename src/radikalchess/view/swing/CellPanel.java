package radikalchess.view.swing;

import radikalchess.model.Cell;
import radikalchess.model.Pieces.Piece;
import radikalchess.model.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
        this.pressed = false;
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

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public Position getPosition() {
        return cell.getPosition();
    }

    public void preparePieceButton() {
        pieceButton.setBackground(color);
        this.add(pieceButton);
    }

    public void addActionListener(ActionListener l) {
        pieceButton.addActionListener(l);
    }
}
