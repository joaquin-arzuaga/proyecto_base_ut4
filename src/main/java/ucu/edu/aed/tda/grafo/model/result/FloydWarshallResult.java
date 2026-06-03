package ucu.edu.aed.tda.grafo.model.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Resultado de ejecutar Floyd-Warshall o Warshall.
 * Mantiene costos mínimos y una tabla de siguiente vértice para reconstruir caminos.
 *
 * @param <V> tipo de los vértices
 */
public class FloydWarshallResult<V> implements IFloydWarshallResult<V> {

    private final Map<V, Map<V, Double>> costs;
    private final Map<V, Map<V, V>> next;

    public FloydWarshallResult(Map<V, Map<V, Double>> costs, Map<V, Map<V, V>> next) {
        this.costs = copyCosts(costs);
        this.next = copyNext(next);
    }

    @Override
    public List<V> getPath(V source, V target) {
        if (source == null || target == null || !connected(source, target)) {
            return Collections.emptyList();
        }

        List<V> path = new ArrayList<>();
        V current = source;
        path.add(current);

        while (!current.equals(target)) {
            Map<V, V> row = next.get(current);
            if (row == null || row.get(target) == null) {
                return Collections.emptyList();
            }
            current = row.get(target);
            path.add(current);

            // Protección ante tablas inconsistentes.
            if (path.size() > costs.size() + 1) {
                return Collections.emptyList();
            }
        }

        return path;
    }

    @Override
    public double getCost(V source, V target) {
        Map<V, Double> row = costs.get(source);
        if (row == null) {
            return Double.POSITIVE_INFINITY;
        }
        return row.getOrDefault(target, Double.POSITIVE_INFINITY);
    }

    @Override
    public boolean connected(V source, V target) {
        return !Double.isInfinite(getCost(source, target));
    }

    private Map<V, Map<V, Double>> copyCosts(Map<V, Map<V, Double>> original) {
        Map<V, Map<V, Double>> copy = new HashMap<>();
        for (Map.Entry<V, Map<V, Double>> entry : original.entrySet()) {
            copy.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }
        return copy;
    }

    private Map<V, Map<V, V>> copyNext(Map<V, Map<V, V>> original) {
        Map<V, Map<V, V>> copy = new HashMap<>();
        for (Map.Entry<V, Map<V, V>> entry : original.entrySet()) {
            copy.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }
        return copy;
    }
}
