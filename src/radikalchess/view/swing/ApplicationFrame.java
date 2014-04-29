package radikalchess.view.swing;

import radikalchess.ai.RadikalChessGame;
import radikalchess.model.Move;
import radikalchess.model.SaveGame;
import radikalchess.model.SaveGameList;
import radikalchess.persistence.FileSaveGameListLoader;
import radikalchess.persistence.PlayListLoader;
import radikalchess.persistence.PlayMaker;
import radikalchess.persistence.SaveGameMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * This class represents the main panel of the game. It is the support of the entire user interface based on Swing
 *
 * @author Jose Luis Molina
 * @see radikalchess.view.swing.BoardPanel
 */
public class ApplicationFrame extends JFrame {

    private BoardPanel boardPanel;
    private RadikalChessGame radikalChessGame;
    private SaveGameMaker saveGameMaker;
    private FileSaveGameListLoader fileGameListLoader;
    private PlayMaker playMaker;
    private PlayListLoader playListLoader;

    /**
     * Constructor charge of showing all the main frame and their respective panels and visors
     *
     * @param radikalChessGame
     * @param fileGameListLoader
     */
    public ApplicationFrame(RadikalChessGame radikalChessGame, SaveGameMaker saveGameMaker, FileSaveGameListLoader fileGameListLoader,
                            PlayMaker playMaker, PlayListLoader playListLoader) {
        this.radikalChessGame = radikalChessGame;
        this.saveGameMaker = saveGameMaker;
        this.fileGameListLoader = fileGameListLoader;
        this.playMaker = playMaker;
        this.playListLoader = playListLoader;

        this.setTitle("RadikalChess");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(400, 650));
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        this.add(createToolbar(), BorderLayout.NORTH);
        this.add(createBoardPanel(), BorderLayout.CENTER);
        this.add(createLoadAndSaveToolbar(), BorderLayout.SOUTH);
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
        jPanel.add(createResetButton());
        jPanel.add(createPlayButton());
        jPanel.add(createMakeDecisionButton());
        jPanel.add(createRankingButton());
        return jPanel;
    }

    private JPanel createLoadAndSaveToolbar() {
        JPanel jPanel = new JPanel();
        JComboBox<SaveGame> loadGameComboBox = createLoadComboBox();
        jPanel.add(loadGameComboBox);
        jPanel.add(new JLabel("Or"));
        jPanel.add(createSaveButton(loadGameComboBox));
        jPanel.setBackground(Color.WHITE);
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
                boardPanel.update(radikalChessGame.getInitialStatus());
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

    private JButton createRankingButton() {
        JButton makeDecisionButton = new JButton("★");
        makeDecisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RankingFrame(playMaker, playListLoader);
            }
        });
        return makeDecisionButton;
    }

    /**
     * Create a button that allows save the game status
     *
     * @return the JButton created
     */
    private JButton createSaveButton(final JComboBox saveGameComboBox) {
        JButton makeDecisionButton = new JButton("Save game");
        makeDecisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGameMaker.save(radikalChessGame);
                SaveGameList.getInstance().clear();
                fileGameListLoader.load();
                populateLoadGameComboBox(saveGameComboBox);
            }
        });

        saveGameComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED)
                    return;
                if (saveGameComboBox.getSelectedItem() instanceof SaveGame)
                    boardPanel.update((((SaveGame) saveGameComboBox.getSelectedItem()).getGame().getActualStatus()));
            }
        });
        return makeDecisionButton;
    }

    /**
     * Create a button that allows load a game status
     *
     * @return the JButton created
     */
    private JComboBox createLoadComboBox() {
        JComboBox saveGames = new JComboBox();
        fileGameListLoader.load();
        saveGames.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SaveGameList.getInstance().clear();
                fileGameListLoader.load();
            }
        });
        populateLoadGameComboBox(saveGames);
        return saveGames;
    }

    private void populateLoadGameComboBox(JComboBox saveGamesComboBox) {
        saveGamesComboBox.removeAllItems();
        saveGamesComboBox.addItem("Select game to load");
        for (int i = 0; i < SaveGameList.getInstance().size(); i++) {
            saveGamesComboBox.addItem(SaveGameList.getInstance().get(i));
        }

    }

    /**
     * Create the complete board panel
     *
     * @return the JPanel created
     */
    private JPanel createBoardPanel() {
        boardPanel = new BoardPanel(radikalChessGame.getActualStatus(), playMaker);
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
            System.out.println(radikalChessGame.getUtility(radikalChessGame.getActualStatus(), radikalChessGame.getActualStatus().getCurrentPlayer()));
            radikalChessGame.move(move);
            System.out.println((move.toString() + "\n" + radikalChessGame.getWhitePlayerSearch().getMetrics() + "\n"));
        } else {
            move = (Move) radikalChessGame.getBlackPlayerSearch().makeDecision(
                    radikalChessGame.getActualStatus());
            System.out.println(radikalChessGame.getUtility(radikalChessGame.getActualStatus(), radikalChessGame.getActualStatus().getCurrentPlayer()));
            radikalChessGame.move(move);
            System.out.println(move.toString() + "\n" + radikalChessGame.getBlackPlayerSearch().getMetrics() + "\n");
        }
    }

}
