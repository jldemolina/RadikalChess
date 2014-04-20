package radikalchess.ai;

import radikalchess.ai.search.AdversarialSearch;
import radikalchess.model.Move;
import radikalchess.model.Player;

import java.util.List;

public class RadikalChessGame implements Game<RadikalChessStatus, Move, Player> {

    private RadikalChessStatus actualStatus;
    private Heuristic whitePlayerHeuristic;
    private Heuristic blackPlayerHeuristic;
    private AdversarialSearch whitePlayerSearch;
    private AdversarialSearch blackPlayerSearch;

    public RadikalChessGame(RadikalChessStatus radikalChessStatus) {
        actualStatus = radikalChessStatus;
    }

    public void setWhitePlayerSearch(AdversarialSearch whitePlayerSearch) {
        this.whitePlayerSearch = whitePlayerSearch;
    }

    public void setBlackPlayerSearch(AdversarialSearch blackPlayerSearch) {
        this.blackPlayerSearch = blackPlayerSearch;
    }

    public AdversarialSearch getWhitePlayerSearch() {
        return whitePlayerSearch;
    }

    public AdversarialSearch getBlackPlayerSearch() {
        return blackPlayerSearch;
    }

    public Heuristic getWhitePlayerHeuristic() {
        return whitePlayerHeuristic;
    }

    public void setWhitePlayerHeuristic(Heuristic whitePlayerHeuristic) {
        this.whitePlayerHeuristic = whitePlayerHeuristic;
    }

    public Heuristic getBlackPlayerHeuristic() {
        return blackPlayerHeuristic;
    }

    public void setBlackPlayerHeuristic(Heuristic blackPlayerHeuristic) {
        this.blackPlayerHeuristic = blackPlayerHeuristic;
    }

    public RadikalChessStatus getActualStatus() {
        return actualStatus;
    }

    /**
     * TODO
     */
    @Override
    public RadikalChessStatus getInitialState() {
        return null; //new RadikalChessStatus(); // new RadikalChessStatus();
    }

    @Override
    public Player[] getPlayers() {
        Player[] players = new Player[2];
        players[0] = actualStatus.getPlayerA();
        players[1] = actualStatus.getPlayerB();
        return players;
    }

    @Override
    public Player getPlayer(RadikalChessStatus state) {
        return state.getCurrentPlayer();
    }

    @Override
    public List<Move> getActions(RadikalChessStatus state) {
        return state.getPossibleMovements();
    }

    @Override
    public RadikalChessStatus getResult(RadikalChessStatus state, Move action) {
        RadikalChessStatus result = null;
        try {
            result = (RadikalChessStatus) state.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        result.move(action);
        return result;
    }

    @Override
    public boolean isTerminal(RadikalChessStatus state) {
        return state.isTerminal();
    }

    @Override
    public double getUtility(RadikalChessStatus state, Player player) {
        if (player.equals(actualStatus.getPlayerA())) {
            return whitePlayerHeuristic.getValue(state, player);
        } else {
            return blackPlayerHeuristic.getValue(state, player);
        }
    }

    public void move(Move move) {
        actualStatus.move(move);
    }

}
