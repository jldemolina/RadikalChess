package radikalchess.view.swing;

import radikalchess.model.Play;
import radikalchess.model.PlayList;
import radikalchess.persistence.PlayListLoader;
import radikalchess.persistence.PlayMaker;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;

/**
 * This class represents the main panel of the game. It is the support of the entire user interface based on Swing
 *
 * @author Jose Luis Molina
 * @see BoardPanel
 */
public class RankingFrame extends JFrame {

    private PlayMaker playMaker;
    private PlayListLoader playListLoader;

    public RankingFrame(PlayMaker playMaker, PlayListLoader playListLoader) {
        this.playMaker = playMaker;
        this.playListLoader = playListLoader;

        this.setTitle("Hall of Fame");
        this.setMinimumSize(new Dimension(400, 500));
        this.setVisible(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        this.add(createPlayPanel(), BorderLayout.CENTER);
        this.add(createWarningPanel(), BorderLayout.SOUTH);

        revalidate();
    }

    private JPanel createPlayPanel() {
        JPanel playPanel = new JPanel();
        PlayList.getInstance().clear();
        playListLoader.load();
        sortElements();
        for (Play play : PlayList.getInstance()) {
            playPanel.add(new JLabel("✔ Player name: " + play.getPlayer().getName()
                    + "\t Number of Movements: " + play.getNumberOfMovements()));
        }
        return playPanel;
    }

    private JPanel createWarningPanel() {
        JPanel warningPanel = new JPanel();
        warningPanel.add(new JLabel("► No se guardan en el Ranking las partidas cargadas ◄"));
        warningPanel.setBackground(Color.YELLOW);
        return warningPanel;
    }

    private void sortElements() {
        PlayList.getInstance().sort(new Comparator<Play>() {
            @Override
            public int compare(Play o1, Play o2) {
                if (o1.getNumberOfMovements() > o2.getNumberOfMovements()) return -1;
                else if (o1.getNumberOfMovements() < o2.getNumberOfMovements()) return 1;
                return 0;
            }
        });

    }


}
