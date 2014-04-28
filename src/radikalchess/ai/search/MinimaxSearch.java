package radikalchess.ai.search;

import radikalchess.ai.Game;
import radikalchess.ai.Metrics;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 169.<br>
 * <p/>
 * <pre>
 * <code>
 * function MINIMAX-DECISION(state) returns an action
 *   return argmax_[a in ACTIONS(s)] MIN-VALUE(RESULT(state, a))
 *
 * function MAX-VALUE(state) returns a utility value
 *   if TERMINAL-TEST(state) then return UTILITY(state)
 *   v = -infinity
 *   for each a in ACTIONS(state) do
 *     v = MAX(v, MIN-VALUE(RESULT(s, a)))
 *   return v
 *
 * function MIN-VALUE(state) returns a utility value
 *   if TERMINAL-TEST(state) then return UTILITY(state)
 *     v = infinity
 *     for each a in ACTIONS(state) do
 *       v  = MIN(v, MAX-VALUE(RESULT(s, a)))
 *   return v
 * </code>
 * </pre>
 * <p/>
 * Figure 5.3 An algorithm for calculating minimax decisions. It returns the
 * action corresponding to the best possible move, that is, the move that leads
 * to the outcome with the best utility, under the assumption that the opponent
 * plays to minimize utility. The functions MAX-VALUE and MIN-VALUE go through
 * the whole game tree, all the way to the leaves, to determine the backed-up
 * value of a state. The notation argmax_[a in S] f(a) computes the element a of
 * set S that has the maximum value of f(a).
 *
 * @param <STATE>  Type which is used for states in the game.
 * @param <ACTION> Type which is used for actions in the game.
 * @param <Player> Type which is used for players in the game.
 * @author Ruediger Lunde
 */
public class MinimaxSearch<STATE, ACTION, Player> implements
        AdversarialSearch<STATE, ACTION> {

    private Game<STATE, ACTION, Player> game;
    private int expandedNodes;
    private int totalExpandedNodes;
    private double time;
    private double totalTime;
    private int movements;

    /**
     * Creates a new search object for a given game.
     */
    public static <STATE, ACTION, Player> MinimaxSearch<STATE, ACTION, Player> createFor(
            Game<STATE, ACTION, Player> game) {
        return new MinimaxSearch<STATE, ACTION, Player>(game);
    }

    public MinimaxSearch(Game<STATE, ACTION, Player> game) {
        this.game = game;
    }

    @Override
    public ACTION makeDecision(STATE state) {
        expandedNodes = 0;
        ACTION result = null;
        time = System.currentTimeMillis();
        double resultValue = Double.NEGATIVE_INFINITY;
        Player player = game.getPlayer(state);
        for (ACTION action : game.getActions(state)) {
            double value = minValue(game.getResult(state, action), player, 0);
            if (value >= resultValue) {
                result = action;
                resultValue = value;
            }
        }
        movements++;
        time = System.currentTimeMillis() - time;
        totalTime += time;
        return result;
    }

    public double maxValue(STATE state, Player player, int p) {
        expandedNodes++;
        totalExpandedNodes++;
        p++;
        if (game.isTerminal(state) || p > P)
            return game.getUtility(state, player);
        double value = Double.NEGATIVE_INFINITY;
        for (ACTION action : game.getActions(state))
            value = Math.max(value,
                    minValue(game.getResult(state, action), player, p));
        return value;
    }

    public double minValue(STATE state, Player player, int p) {
        expandedNodes++;
        totalExpandedNodes++;
        p++;
        if (game.isTerminal(state) || p > P)
            return game.getUtility(state, player);
        double value = Double.POSITIVE_INFINITY;
        for (ACTION action : game.getActions(state))
            value = Math.min(value,
                    maxValue(game.getResult(state, action), player, p));
        return value;
    }

    @Override
    public Metrics getMetrics() {
        Metrics result = new Metrics();
        result.set("expandedNodes", expandedNodes);
        result.set("totalExpandedNodes", totalExpandedNodes);
        result.set("time", time / 1000);
        result.set("totalTime", totalTime / 1000);
        result.set("movements", movements);
        return result;
    }
}
