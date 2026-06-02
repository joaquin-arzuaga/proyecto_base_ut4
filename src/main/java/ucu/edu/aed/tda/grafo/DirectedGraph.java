package ucu.edu.aed.tda.grafo;

import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.DirectedEdge;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementacion base de un grafo dirigido.
 *
 * @param <V> tipo de los vertices
 * @param <D> tipo del dato almacenado en cada arista
 */
public class DirectedGraph<V, D> implements IGraph<V, D> {

    /**
     * Lista de adyacencia del grafo.
     *
     */
    private final Map<V, Set<Edge<V, D>>> ListaAdyacencia;

    /**
     * Conjunto de aristas del grafo.
     *
     */
    private final Set<Edge<V, D>> aristas;

    public DirectedGraph() {
        this.ListaAdyacencia = new HashMap<>();
        this.aristas = new HashSet<>();
    }

    @Override
    public boolean agregarVertice(V vertex) {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public V buscarVertice(Comparable<V> criterio) {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public boolean agregarArista(V source, V target, D dato) {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public boolean eliminarArista(Comparable<V> source, Comparable<V> target) {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public boolean removerVertice(Comparable<V> criteria) {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public Set<V> vertices() {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public Set<Edge<V, D>> aristas() {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public boolean existeArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public Edge<V, D> obtenerArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public List<Edge<V, D>> adyacencias(Comparable<V> verticeCriteria) {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public boolean esConexo() {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public void vaciar() {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public boolean tieneCiclos() {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

}
