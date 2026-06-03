package ucu.edu.aed.tda.grafo;

import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.DirectedEdge;
import ucu.edu.aed.tda.grafo.model.edge.Edge;

import java.util.ArrayList;
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
       if (vertex !=null && !ListaAdyacencia.containsKey(vertex)) {
           ListaAdyacencia.put(vertex, new HashSet<>());
           return true;
       }
         return false;
    }

    @Override
    public V buscarVertice(Comparable<V> criterio) {
       if (criterio != null) {
        for(V vertice : ListaAdyacencia.keySet()) {
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
            if (ListaAdyacencia.containsKey(source) && ListaAdyacencia.containsKey(target)){
                Edge<V,D> arista = new DirectedEdge<>(source, target, dato);
                if (!aristas.contains(arista)){
                    ListaAdyacencia.get(source).add(arista);
                    aristas.add(arista);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean eliminarArista(Comparable<V> source, Comparable<V> target) {
        Edge<V,D> arustaAEliminar = obtenerArista(source, target);
        if(arustaAEliminar == null){
            return false;
        }
        ListaAdyacencia.get(arustaAEliminar.source()).remove(arustaAEliminar)
        aristas.remove(arustaAEliminar);
        
        return true;
    }

    @Override
    public boolean removerVertice(Comparable<V> criteria) {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
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
        return obtenerArista(sourceCriteria, targetCriteria) !=null;
    }

    @Override
    public Edge<V, D> obtenerArista(Comparable<V> sourceCriteria, Comparable<V> targetCriteria) {
        if (sourceCriteria == null || targetCriteria == null){
            return null;
        }
        for (Edge<V, D> arista: aristas){
            if (sourceCriteria.compareTo(arista.source())==0 && targetCriteria.compareTo(arista.target())==0){
                return arista;
            }
        }
        return null;
    }

    @Override
    public List<Edge<V, D>> adyacencias(Comparable<V> verticeCriteria) {
        if (verticeCriteria == null){
            return new ArrayList<>();
        }
        V vertice = buscarVertice(verticeCriteria);
        if (vertice == null){
            return new ArrayList<>();
        }
        return new ArrayList<>(ListaAdyacencia.get(vertice));
    }

    @Override
    public boolean esConexo() {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

    @Override
    public void vaciar() {
        ListaAdyacencia.clear();
        aristas.clear();
    }

    @Override
    public boolean tieneCiclos() {
        throw new UnsupportedOperationException("Metodo no implementado todavia");
    }

}
