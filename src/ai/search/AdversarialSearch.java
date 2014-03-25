package ai.search;

import ai.Metrics;

/**
 * Variant of the search interface. Since players can only control the next
 * move, method
 * <code>makeDecision</code> returns only one action, not a sequence of actions.
 *
 * @author Ruediger Lunde
 */
public interface AdversarialSearch<STATE, ACTION> {

    public static final int P = 6;

    /**
     * Returns the action which appears to be the best at the given state.
     */
    ACTION makeDecision(STATE state);

    /**
     * Returns all the metrics of the search.
     *
     * @return all the metrics of the search.
     */
    Metrics getMetrics();
}
