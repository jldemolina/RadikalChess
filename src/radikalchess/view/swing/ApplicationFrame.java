package radikalchess.view.swing;

import radikalchess.ai.RadikalChessGame;
import radikalchess.model.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the main panel of the game. It is the support of the entire user interface based on Swing
 *
 * @author Jose Luis Molina
 * @see radikalchess.view.swing.BoardPanel
 */
public class ApplicationFrame extends JFrame {

    private BoardPanel boardPanel;
    private RadikalChessGame radikalChessGame;

    public ApplicationFrame(RadikalChessGame radikalChessGame) {
        this.radikalChessGame = radikalChessGame;

        this.setTitle("RadikalChess");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(450, 700));
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        this.add(createToolbar(), BorderLayout.NORTH);
        this.add(createBoardPanel(), BorderLayout.CENTER);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    private JPanel createToolbar() {
        JPanel jPanel = new JPanel();
        jPanel.add(createResetButton());
        jPanel.add(createPlayButton());
        jPanel.add(createMakeDecisionButton());
        return jPanel;
    }

    private JButton createResetButton() {
        JButton resetButton = new JButton("Reset");

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boardPanel.reset();
                if (radikalChessGame.getActualStatus().getCurrentPlayer().equals(radikalChessGame.getActualStatus().getPlayerB()))
                    radikalChessGame.getActualStatus().alternatePlayer();
                revalidate();
            }
        });
        return resetButton;
    }

    private JButton createPlayButton() {
        JButton playButton = new JButton("Play");

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (!radikalChessGame.getActualStatus().isTerminal()) {
                    decideMovement();
                    boardPanel.update();
                }
            }
        });
        return playButton;
    }

    private JButton createMakeDecisionButton() {
        JButton makeDecisionButton = new JButton("Make decision");
        makeDecisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!radikalChessGame.getActualStatus().isTerminal()) {
                    decideMovement();
                    boardPanel.update();
                }
            }
        });
        return makeDecisionButton;
    }

    private JPanel createBoardPanel() {
        boardPanel = new BoardPanel(radikalChessGame.getActualStatus());
        boardPanel.setSize(400, 600);
        return boardPanel;
    }

    private void decideMovement() {
        Move move;
        if (radikalChessGame.getPlayer(radikalChessGame.getActualStatus()).equals(radikalChessGame.getActualStatus().getPlayerA())) {
            move = (Move) radikalChessGame.getWhitePlayerSearch().makeDecision(
                    radikalChessGame.getActualStatus());
            radikalChessGame.move(move);
            System.out.println(move.toString() + "\n" + radikalChessGame.getWhitePlayerSearch().getMetrics() + "\n");
        } else {
            move = (Move) radikalChessGame.getBlackPlayerSearch().makeDecision(
                    radikalChessGame.getActualStatus());
            radikalChessGame.move(move);
            System.out.println(move.toString() + "\n" + radikalChessGame.getBlackPlayerSearch().getMetrics() + "\n");
        }
        radikalChessGame.getActualStatus().alternatePlayer();
    }

}
