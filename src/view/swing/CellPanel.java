package view.swing;

import model.Pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class CellPanel extends JPanel {
    private int x, y;
    private Color color;
    private JButton pieceButton;
    private Piece piece;

    public CellPanel(int x, int y, Color color) {
        this.pieceButton = new JButton();
        pieceButton.setBackground(color);
        this.add(pieceButton);
        this.x = x;
        this.y = y;
        this.color = color;
        this.setBackground(color);
        this.setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

    public void addPiece(Piece piece) {
        try {
            this.piece = piece;
            pieceButton.setIcon(new ImageIcon(ImageIO.read(new ByteArrayInputStream(piece.getImage().getBitmap().getByteArray()))));
        } catch (IOException ex) {
        }
        this.repaint();
    }

    public void removePiece() {
        if (piece != null) {
            piece = null;
            pieceButton.setIcon(null);
            repaint();
        }
    }

    public int getXPos() {
        return x;
    }

    public int getYPos() {
        return y;
    }

}
