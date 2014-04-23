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

    /**
     * Constructor charge of showing all the main frame and their respective panels and visors
     *
     * @param radikalChessGame
     */
    public ApplicationFrame(RadikalChessGame radikalChessGame) {
        this.radikalChessGame = radikalChessGame;

        this.setTitle("RadikalChess");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(550, 800));
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        this.add(createToolbar(), BorderLayout.NORTH);
        this.add(createBoardPanel(), BorderLayout.CENTER);
        revalidate();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    /**
     * Create the toolbar that contains the action buttons
     *
     * @return the JPanel created
     */
    private JPanel createToolbar() {
        JPanel jPanel = new JPanel();
        // jPanel.add(createResetButton());
        jPanel.add(createPlayButton());
        jPanel.add(createMakeDecisionButton());
        return jPanel;
    }

    /**
     * Create a button to reset the board, the pieces, the cells ... In short, the full game.
     *
     * @return the JButton created
     */
    private JButton createResetButton() {
        JButton resetButton = new JButton("Reset");

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                radikalChessGame.setActualStatus(radikalChessGame.getInitialState());
                boardPanel.reset();
                revalidate();
            }
        });
        return resetButton;
    }

    /**
     * Create a button that allows the AI to play a game against itself
     *
     * @return the JButton created
     */
    private JButton createPlayButton() {
        JButton playButton = new JButton("Play");

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (!radikalChessGame.getActualStatus().isTerminal()) {
                    System.out.println("Thinking... and solving...");
                    decideMovement();
                    boardPanel.update();
                }
            }
        });
        return playButton;
    }

    /**
     * Create a button that allows the AI to play a turn.
     *
     * @return the JButton created
     */
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

    /**
     * Create the complete board panel
     *
     * @return the JPanel created
     */
    private JPanel createBoardPanel() {
        boardPanel = new BoardPanel(radikalChessGame.getActualStatus());
        boardPanel.setSize(400, 600);
        return boardPanel;
    }

    /**
     * Permits from search algorithms and artificial intelligence, moving a record
     */
    private void decideMovement() {
        Move move;
        if (radikalChessGame.getPlayer(radikalChessGame.getActualStatus()).equals(radikalChessGame.getActualStatus().getPlayerA())) {
            move = (Move) radikalChessGame.getWhitePlayerSearch().makeDecision(
                    radikalChessGame.getActualStatus());
            radikalChessGame.move(move);
            System.out.println((move.toString() + "\n" + radikalChessGame.getWhitePlayerSearch().getMetrics() + "\n"));
        } else {
            move = (Move) radikalChessGame.getBlackPlayerSearch().makeDecision(
                    radikalChessGame.getActualStatus());
            radikalChessGame.move(move);
            System.out.println(move.toString() + "\n" + radikalChessGame.getBlackPlayerSearch().getMetrics() + "\n");
        }
    }

}
