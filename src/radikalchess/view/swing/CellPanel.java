package radikalchess.view.swing;

import radikalchess.model.Cell;
import radikalchess.model.Position;
import radikalchess.model.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * This class represents a simple cell panel of the game.
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 */
public class CellPanel extends JPanel {
    private Cell cell;
    private Color background;
    private JButton pieceButton;
    private Piece piece;
    private boolean pressed;

    public CellPanel(Cell cell, Color background) {
        this.cell = cell;
        this.pieceButton = new JButton();
        this.background = background;
        this.pressed = false;
        this.setBackground(background);
        this.setVisible(true);
        preparePieceButton();
    }

    public void update() {
        try {
            if (cell.getPiece() != null) {
                this.piece = cell.getPiece();
                pieceButton.setIcon(new ImageIcon(ImageIO.read(new ByteArrayInputStream(piece.getImage().getBitmap().getByteArray()))));
            } else {
                this.piece = null;
                cell.setPiece(null);
                pieceButton.setIcon(null);
            }
        } catch (IOException ex) {
        }
        revalidate();
    }

    public void addPiece(Piece piece) {
        try {
            cell.setPiece(piece);
            piece.setPosition(cell.getPosition());
            this.piece = piece;
            pieceButton.setIcon(new ImageIcon(ImageIO.read(new ByteArrayInputStream(piece.getImage().getBitmap().getByteArray()))));
        } catch (IOException ex) {
        }
        revalidate();
    }

    public void removePiece(boolean removeInOriginalCell) {
        if (piece != null) {
            if (removeInOriginalCell) cell.setPiece(null);
            piece = null;
            pieceButton.setIcon(null);
            revalidate();
        }
    }


    public Piece getPiece() {
        return piece;
    }

    public boolean hasAnyPiece() {
        return piece != null;
    }

    public void setPressed(boolean pressed) {
        this.pieceButton.setSelected(pressed);
        this.pressed = pressed;
    }

    public boolean isPressed() {
        return pressed;
    }

    public Position getPosition() {
        return cell.getPosition();
    }

    public void preparePieceButton() {
        pieceButton.setBackground(background);
        this.add(pieceButton);
    }

    public void addActionListener(ActionListener l) {
        pieceButton.addActionListener(l);
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
        repaint();
    }


}
