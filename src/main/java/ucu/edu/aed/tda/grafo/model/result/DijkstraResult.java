package ucu.edu.aed.tda.grafo.model.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Resultado de ejecutar el algoritmo de Dijkstra desde un vértice origen.
 *
 * @param <V> tipo de los vértices
 */
public class DijkstraResult<V> implements IDijkstraResult<V> {

    private final V source;
    private final Map<V, Double> costs;
    private final Map<V, V> previous;

    public DijkstraResult(V source, Map<V, Double> costs, Map<V, V> previous) {
        this.source = source;
        this.costs = new HashMap<>(costs);
        this.previous = new HashMap<>(previous);
    }

    @Override
    public double getCost(V otherVertex) {
        return costs.getOrDefault(otherVertex, Double.POSITIVE_INFINITY);
    }

    @Override
    public List<V> getPath(V otherVertex) {
        if (otherVertex == null || !costs.containsKey(otherVertex) || Double.isInfinite(getCost(otherVertex))) {
            return Collections.emptyList();
        }

        LinkedList<V> path = new LinkedList<>();
        V current = otherVertex;

        while (current != null) {
            path.addFirst(current);
            if (current.equals(source)) {
                break;
            }
            current = previous.get(current);
        }

        if (path.isEmpty() || !path.getFirst().equals(source)) {
            return Collections.emptyList();
        }

        return new ArrayList<>(path);
    }
}
