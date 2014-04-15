package radikalchess.view.swing;

import radikalchess.model.Board;
import radikalchess.model.Player;

import javax.swing.*;
import java.awt.*;

/**
 * This class represents the main panel of the game. It is the support of the entire user interface based on Swing
 *
 * @author Jose Luis Molina
 * @see radikalchess.view.swing.BoardPanel
 */
public class ApplicationFrame extends JFrame {

    private BoardPanel boardPanel;
    private Board board;
    private Player playerA;
    private Player playerB;

    public ApplicationFrame(Player playerA, Player playerB, Board board) {
        this.board = board;
        this.playerA = playerA;
        this.playerB = playerB;

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
        jPanel.add(createPlayButton());
        jPanel.add(createMakeDecisionButton());
        return jPanel;
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
        boardPanel = new BoardPanel(board, playerA, playerB);
        boardPanel.setSize(400, 600);
        return boardPanel;
    }

}
