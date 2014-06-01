package radikalchess.view.swing;

import radikalchess.ai.RadikalChessGame;
import radikalchess.ai.heuristics.AttackHeuristic;
import radikalchess.ai.heuristics.BadPlayerHeuristic;
import radikalchess.ai.heuristics.MediumHeuristic;
import radikalchess.ai.search.AlphaBetaSearch;
import radikalchess.ai.search.MinimaxSearch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * This class represents the configuration panel of the game.
 *
 * @author Jose Luis Molina
 * @author Eduardo Mendoza García
 */
public class ConfigurationPanel extends JFrame {
    private RadikalChessGame game;

    public ConfigurationPanel(RadikalChessGame game) {
        this.game = game;

        this.setTitle("Configuration");
        this.setMinimumSize(new Dimension(590, 100));
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        this.add(createPanel(), BorderLayout.CENTER);

        revalidate();
    }

    private JPanel createPanel() {
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("Configuración del jugador A"));
        jPanel.add(createPlayerAHeutisticsComboBox());
        jPanel.add(createPlayerASearchComboBox());
        jPanel.add(new JLabel("Configuración del jugador B"));
        jPanel.add(createPlayerBHeutisticsComboBox());
        jPanel.add(createPlayerBSearchComboBox());
        return jPanel;
    }

    private JComboBox createPlayerASearchComboBox() {
        final JComboBox comboBox = new JComboBox();
        comboBox.addItem("Seleccionar búsqueda");
        comboBox.addItem("Minimax");
        comboBox.addItem("Alpha-Beta (3)");
        comboBox.addItem("Alpha-Beta (4)");
        comboBox.addItem("Alpha-Beta (5)");
        comboBox.addItem("Alpha-Beta (6)");
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED)
                    return;
                if (comboBox.getSelectedItem().equals("Minimax"))
                    game.setWhitePlayerSearch(new MinimaxSearch(game));
                else if (comboBox.getSelectedItem().equals("Alpha-Beta (3)"))
                    game.setWhitePlayerSearch(new AlphaBetaSearch(game, 3));
                else if (comboBox.getSelectedItem().equals("Alpha-Beta (4)"))
                    game.setWhitePlayerSearch(new AlphaBetaSearch(game, 4));
                else if (comboBox.getSelectedItem().equals("Alpha-Beta (5)"))
                    game.setWhitePlayerSearch(new AlphaBetaSearch(game, 5));
                else
                    game.setWhitePlayerSearch(new AlphaBetaSearch(game, 6));

            }
        });
        return comboBox;
    }

    private JComboBox createPlayerBSearchComboBox() {
        final JComboBox comboBox = new JComboBox();
        comboBox.addItem("Seleccionar búsqueda");
        comboBox.addItem("Minimax");
        comboBox.addItem("Alpha-Beta (3)");
        comboBox.addItem("Alpha-Beta (4)");
        comboBox.addItem("Alpha-Beta (5)");
        comboBox.addItem("Alpha-Beta (6)");
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED)
                    return;
                if (comboBox.getSelectedItem().equals("Minimax"))
                    game.setBlackPlayerSearch(new MinimaxSearch(game));
                else if (comboBox.getSelectedItem().equals("Alpha-Beta (3)"))
                    game.setBlackPlayerSearch(new AlphaBetaSearch(game, 3));
                else if (comboBox.getSelectedItem().equals("Alpha-Beta (4)"))
                    game.setBlackPlayerSearch(new AlphaBetaSearch(game, 4));
                else if (comboBox.getSelectedItem().equals("Alpha-Beta (5)"))
                    game.setBlackPlayerSearch(new AlphaBetaSearch(game, 5));
                else
                    game.setBlackPlayerSearch(new AlphaBetaSearch(game, 6));

            }
        });
        return comboBox;
    }

    private JComboBox createPlayerBHeutisticsComboBox() {
        final JComboBox comboBox = new JComboBox();
        comboBox.addItem("Seleccionar heurística");
        comboBox.addItem("Medium Heuristic");
        comboBox.addItem("BadPlayer Heuristic");
        comboBox.addItem("Attack Heuristic");
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED)
                    return;
                if (comboBox.getSelectedItem().equals("BadPlayer Heuristic"))
                    game.setBlackPlayerHeuristic(new BadPlayerHeuristic());
                else if (comboBox.getSelectedItem().equals("Medium Heuristic"))
                    game.setBlackPlayerHeuristic(new MediumHeuristic());
                else
                    game.setBlackPlayerHeuristic(new AttackHeuristic());

            }
        });
        return comboBox;
    }

    private JComboBox createPlayerAHeutisticsComboBox() {
        final JComboBox comboBox = new JComboBox();
        comboBox.addItem("Seleccionar heurística");
        comboBox.addItem("Medium Heuristic");
        comboBox.addItem("BadPlayer Heuristic");
        comboBox.addItem("Attack Heuristic");
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() != ItemEvent.SELECTED)
                    return;
                if (comboBox.getSelectedItem().equals("BadPlayer Heuristic"))
                    game.setBlackPlayerHeuristic(new BadPlayerHeuristic());
                else if (comboBox.getSelectedItem().equals("Medium Heuristic"))
                    game.setBlackPlayerHeuristic(new MediumHeuristic());
                else
                    game.setBlackPlayerHeuristic(new AttackHeuristic());

            }
        });
        return comboBox;
    }


}
