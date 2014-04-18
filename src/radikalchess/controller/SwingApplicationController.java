package radikalchess.controller;

import radikalchess.ai.RadikalChessGame;
import radikalchess.ai.RadikalChessHeuristic;
import radikalchess.ai.RadikalChessStatus;
import radikalchess.ai.search.AlphaBetaSearch;
import radikalchess.model.Board;
import radikalchess.model.Player;
import radikalchess.view.swing.ApplicationFrame;

/**
 * This class is responsible for the total control of the application. It connects the model and the view (and, of course, the IA)
 *
 * @author Jose Luis Molina
 */
public class SwingApplicationController {

    public void execute() {
        RadikalChessStatus status = new RadikalChessStatus(new Board(6, 4), new Player("White player"), new Player("Black player"));
        RadikalChessGame game = new RadikalChessGame(status);
        game.setBlackPlayerHeuristic(new RadikalChessHeuristic());
        game.setWhitePlayerHeuristic(new RadikalChessHeuristic());
        game.setBlackPlayerSearch(new AlphaBetaSearch(game, 2));
        game.setWhitePlayerSearch(new AlphaBetaSearch(game, 2));
        new ApplicationFrame(game);
    }

}
