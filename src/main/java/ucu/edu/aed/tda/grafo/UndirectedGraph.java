package ucu.edu.aed.tda.grafo;

import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.UndirectedEdge;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementacion base de un grafo dirigido.
 *
 * @param <V> tipo de los vertices
 * @param <D> tipo del dato almacenado en cada arista
 */
public class UndirectedGraph<V, D> implements IUndirectedGraph<V, D> {

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

    public UndirectedGraph() {
        this.ListaAdyacencia = new HashMap<>();
        this.aristas = new HashSet<>();
    }

    @Override
    public boolean agregarVertice(V vertex) {
        if (vertex != null && !ListaAdyacencia.containsKey(vertex)) {
            ListaAdyacencia.put(vertex, new HashSet<>());
            return true;
        }
        return false;
    }

    @Override
    public V buscarVertice(Comparable<V> criterio) {
        if (criterio != null) {
            for (V vertice : ListaAdyacencia.keySet()) {
                if (criterio.compareTo(vertice) == 0) {
                    return vertice;
                }
            }
        }
        return null;
    }

    @Override
    public boolean agregarArista(V source, V target, D dato) {
        if (source != null && target != null && dato != null) {
            if (ListaAdyacencia.containsKey(source) && ListaAdyacencia.containsKey(target)) {

                Edge<V, D> arista = new UndirectedEdge<>(source, target, dato);

                if (!aristas.contains(arista)) {
                    ListaAdyacencia.get(source).add(arista);
                    ListaAdyacencia.get(target).add(arista);
                    aristas.add(arista);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean eliminarArista(Comparable<V> source, Comparable<V> target) {
        Edge<V, D> arustaAEliminar = obtenerArista(source, target);
        if (arustaAEliminar == null) {
            return false;
        }

        ListaAdyacencia.get(arustaAEliminar.source()).remove(arustaAEliminar);
        ListaAdyacencia.get(arustaAEliminar.target()).remove(arustaAEliminar);
        aristas.remove(arustaAEliminar);

        return true;
    }

    @Override
    public boolean removerVertice(Comparable<V> criteria) {
        if (criteria == null) {
            return false;
        }

        V vertice = buscarVertice(criteria);

        if (vertice == null || !ListaAdyacencia.containsKey(vertice)) {
            return false;
        }

        Set<Edge<V, D>> aristasSalientesDelVertice = new HashSet<>(ListaAdyacencia.get(vertice));

        for (Edge<V, D> arista : aristasSalientesDelVertice) {
            V otroVertice;
            if (arista.source().equals(vertice)) {
                otroVertice = arista.target();
            } else {
                otroVertice = arista.source();
            }

            if (ListaAdyacencia.containsKey(otroVertice)) {
                ListaAdyacencia.get(otroVertice).remove(arista);
            }

            aristas.remove(arista);
        }

        ListaAdyacencia.remove(vertice);

        return true;
    }

    @Override
    public Set<V> vertices() {
        return new HashSet<>(ListaAdyacencia.keySet());
    }

    @Override
    public Set<Edge<V, D>> aristas() {
        return new HashSet<>(aristas);
    }

    @Override
    public boolean existeArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {
        return obtenerArista(sourceCriteria, targetCriteria) != null;
    }

    @Override
    public Edge<V, D> obtenerArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {
        if (sourceCriteria == null || targetCriteria == null) {
            return null;
        }
        for (Edge<V, D> arista : aristas) {
            if ((sourceCriteria.compareTo(arista.source()) == 0 && targetCriteria.compareTo(arista.target()) == 0)
                    || (sourceCriteria.compareTo(arista.target()) == 0
                            && targetCriteria.compareTo(arista.source()) == 0)) {
                return arista;
            }
        }
        return null;
    }

    @Override
    public List<Edge<V, D>> adyacencias(Comparable<V> verticeCriteria) {
        if (verticeCriteria == null) {
            return new ArrayList<>();
        }
        V vertice = buscarVertice(verticeCriteria);
        if (vertice == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(ListaAdyacencia.get(vertice));
    }

    @Override
    public boolean esConexo() {
        HashSet<V> visitados = new HashSet<V>();
        DirectedGraphAlgorithm algorithm = new DirectedGraphAlgorithm();
        for (V vertice : vertices()) {
            visitados.clear();
            algorithm.recorridoEnProfundidad(this, construirComparable(vertice), v -> visitados.add(v));
            if (visitados.size() != vertices().size())
                return false;
        }
        return true;
    }

    @Override
    public void vaciar() {
        ListaAdyacencia.clear();
        aristas.clear();
    }

    @Override
    public boolean tieneCiclos() {
        Set<V> visitados = new HashSet<>();
        Set<Edge<V, D>> aristasVisitadas = new HashSet<>();

        for (V vertice : ListaAdyacencia.keySet()) {
            if (!visitados.contains(vertice)) {
                if (tieneCiclosDesde(vertice, null, visitados, aristasVisitadas)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean tieneCiclosDesde(V vertice, V verticePadre, Set<V> visitados, Set<Edge<V, D>> aristasVisitadas) {
        visitados.add(vertice);

        for (Edge<V, D> arista : ListaAdyacencia.get(vertice)) {
            V verticeSiguiente;
            if (arista.source().equals(vertice)) {
                verticeSiguiente = arista.target();
            } else {
                verticeSiguiente = arista.source();
            }

            if (aristasVisitadas.contains(arista)) {
                continue;
            }

            aristasVisitadas.add(arista);

            if (!visitados.contains(verticeSiguiente)) {
                if (tieneCiclosDesde(verticeSiguiente, vertice, visitados, aristasVisitadas)) {
                    return true;
                }
            } else if (!verticeSiguiente.equals(verticePadre)) {
                return true;
            }
        }

        return false;
    }

}
