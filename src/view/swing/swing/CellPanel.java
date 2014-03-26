package view.swing.swing;

import javax.swing.*;
import java.awt.*;

public class CellPanel extends JPanel {
    private int x, y;
    private Color color;
    private PieceImageViewer pieceImageViewer;

    public CellPanel(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.setBackground(color);
        this.setVisible(true);
    }

    public PieceImageViewer getPieceImageViewer() {
        return pieceImageViewer;
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

    public int getXPos() {
        return x;
    }

    public int getYPos() {
        return y;
    }

    public boolean isVisiblePiece() {
        return this.pieceImageViewer.isVisible();
    }

    public void setPieceImageViewer(PieceImageViewer pieceImageViewer) {
        this.add(pieceImageViewer);
        this.pieceImageViewer = pieceImageViewer;
        pieceImageViewer.setVisible(true);
    }

}
