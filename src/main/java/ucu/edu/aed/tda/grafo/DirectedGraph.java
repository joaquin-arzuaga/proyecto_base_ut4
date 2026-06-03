package ucu.edu.aed.tda.grafo;

import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.DirectedEdge;
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
public class DirectedGraph<V, D> implements IDirectedIGraph<V, D> {

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
        ListaAdyacencia.get(arustaAEliminar.source()).remove(arustaAEliminar);
        aristas.remove(arustaAEliminar);
        
        return true;
    }

    @Override
    public boolean removerVertice(Comparable<V> criteria) {
        if(criteria == null){
            return false;
        }
        V vertice = buscarVertice(criteria);
        Set<Edge<V,D>> aristasSalientesDelVertice = new HashSet<>(ListaAdyacencia.get(vertice));
        for(Edge<V, D> arista : aristasSalientesDelVertice){
            aristas.remove(arista);
        }
        ListaAdyacencia.remove(vertice);
        for(Set<Edge<V,D>> adyacentes : ListaAdyacencia.values()){
            Set<Edge<V,D>> adyacenteAEliminar = new HashSet<>();
            for(Edge<V,D> arista: adyacentes){
                if(arista.target().equals(vertice)){
                    adyacenteAEliminar.add(arista);
                    aristas.remove(arista);
                }
            }
            adyacentes.removeAll(adyacenteAEliminar);
        }
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
        HashSet<V> visitados =  new HashSet<V>();
        DirectedGraphAlgorithm algorithm = new DirectedGraphAlgorithm();
        for (V vertice : vertices()) {
            visitados.clear();
            algorithm.recorridoEnProfundidad(this, construirComparable(vertice), v -> visitados.add(v));
            if (visitados.size() != vertices().size()) return false;
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
        Set<V> enCamino = new HashSet<>();

        for (V vertice : ListaAdyacencia.keySet()) {
            if (!visitados.contains(vertice)) {
                if (tieneCiclosDesde(vertice, visitados, enCamino)) {
                    return true;
                }
            }
        }

      return false;
    }

    private boolean tieneCiclosDesde(V vertice, Set<V> visitados, Set<V> enCamino) {
    visitados.add(vertice);
    enCamino.add(vertice);

    for (Edge<V, D> arista : ListaAdyacencia.get(vertice)) {
        V destino = arista.target();
        if (!visitados.contains(destino)) {
            if (tieneCiclosDesde(destino, visitados, enCamino)) {
                return true;
            }
        } else if (enCamino.contains(destino)) {
            return true;
        }
    }

       enCamino.remove(vertice);
       return false; 
    }


    @Override
    public Set successors(Comparable criteria) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'successors'");
    }

    @Override
    public Set predecessors(Comparable criteria) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'predecessors'");
    }

}
