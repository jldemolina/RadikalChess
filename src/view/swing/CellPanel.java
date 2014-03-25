package view.swing;

import javax.swing.*;
import java.awt.*;

public class CellPanel extends JPanel {
    private int x, y;
    private Color color;
    private JButton button;

    public CellPanel(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.setBackground(color);
        this.setVisible(true);
    }

    public JButton getButton() {
        return button;
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

    public boolean isVisibleButton() {
        return this.button.isVisible();
    }

    public void setButton(JButton button) {
        this.add(button);
        this.button = button;
        button.setVisible(false);
    }

}
