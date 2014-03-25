package view.swing;

import model.Board;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView extends JFrame {

    private BoardPanel boardPanel;
    private Board board;

    public GameView() {
        board = new Board(6, 4);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("RadikalChess");
        Dimension dimension = new Dimension(400, 600);
        setMinimumSize(dimension);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        this.add(createToolbar(), BorderLayout.NORTH);
        this.add(createBoardPanel(), BorderLayout.CENTER);

        pack();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    private void reset() {
        remove(boardPanel);

        createBoardPanel();
        repaint();
        pack();
    }

    private JPanel createToolbar() {
        JPanel jPanel = new JPanel();
        jPanel.add(createPlayButton());
        jPanel.add(createMakeDecisionButton());
        jPanel.add(createResetButton());
        return jPanel;
    }

    private JButton createResetButton() {
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        return resetButton;
    }

    private JButton createPlayButton() {
        JButton playButton = new JButton("Play");
        return playButton;

    }

    private JButton createMakeDecisionButton() {
        JButton makeDecisionButton = new JButton("Make decision");
        return makeDecisionButton;
    }

    private JPanel createBoardPanel() {
        boardPanel = new BoardPanel(board);
        boardPanel.setSize(400, 600);
        boardPanel.setVisible(true);
        return boardPanel;
    }

    private JPanel createLogPanel() {
        LogPanel panel = new LogPanel();
        panel.setBorder(new LineBorder(Color.black));
        panel.setSize(new Dimension(200, 600));
        panel.setVisible(true);
        return panel;
    }


}
