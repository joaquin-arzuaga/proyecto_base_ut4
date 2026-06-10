package ucu.edu.aed.tda.grafo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;

public class UndirectedGraphAlgorithm implements IUndirectedGraphAlgorithm{

    @Override
    public <V, D extends WeightedEdge> IUndirectedGraph<V, D> kruskal(IUndirectedGraph<V, D> graph){
        if (graph == null || graph.vertices().isEmpty()){
            return new UndirectedGraph<>();
        }
        UndirectedGraph<V, D> arbolExpresionMinima = new UndirectedGraph<>();
        for (V vertice : graph.vertices()){
            arbolExpresionMinima.agregarVertice(vertice);
        }
        List<Edge<V,D>> aristasOrdenadas = new ArrayList<>(graph.aristas());

        aristasOrdenadas.sort(Comparator.comparingDouble(arista -> arista.dato().getWeight()));
        for (Edge<V, D> arista : aristasOrdenadas) {

        arbolExpresionMinima.agregarArista(arista.source(),arista.target(),arista.dato() );
        
         if (arbolExpresionMinima.tieneCiclos()) {

            arbolExpresionMinima.eliminarArista(
                arbolExpresionMinima.construirComparable(arista.source()),
                arbolExpresionMinima.construirComparable(arista.target())
            );
        }
    }
        return arbolExpresionMinima;
    }
    
    //prim
    @Override
public <V, D extends WeightedEdge> IUndirectedGraph<V, D> prim(IUndirectedGraph<V, D> graph, Comparable<V> source) {
    IUndirectedGraph<V, D> arbol = new UndirectedGraph<>();

    if (graph == null || source == null) {
        return arbol;
    }

    V origen = graph.buscarVertice(source);

    if (origen == null) {
        return arbol;
    }

    for (V vertice : graph.vertices()) {
        arbol.agregarVertice(vertice);
    }

    Set<V> visitados = new HashSet<>();
    Set<V> noVisitados = new HashSet<>(graph.vertices());

    visitados.add(origen);
    noVisitados.remove(origen);

    while (!noVisitados.isEmpty()) {
        Edge<V, D> menorArista = searchMinEdge(graph, visitados, noVisitados);

        if (menorArista == null) {
            // no hay conexión desde lo visitado -> saltar a otra componente
            V siguienteComponente = noVisitados.iterator().next();
            visitados.add(siguienteComponente);
            noVisitados.remove(siguienteComponente);
            continue;
        }

        arbol.agregarArista(menorArista.source(), menorArista.target(), menorArista.dato());

        V nuevoVertice;

        if (visitados.contains(menorArista.source())) {
            nuevoVertice = menorArista.target();
        } else {
            nuevoVertice = menorArista.source();
        }

        visitados.add(nuevoVertice);
        noVisitados.remove(nuevoVertice);
    }

    return arbol;
}




    //minima arista
    @Override
    public <V, D extends WeightedEdge> Edge<V, D> searchMinEdge(IUndirectedGraph<V, D> graph, Collection<V> U, Collection<V> V) {
        if(graph == null || U == null || V == null || U.isEmpty() || V.isEmpty()){
            return null;
        }
        Edge<V, D> mejorArista = null;
        for (V u : U) {
            for (Edge<V, D> arista : graph.adyacencias(graph.construirComparable(u))){
                V noVisitado = null;
                if(arista.source().equals(u)){
                    noVisitado = arista.target();
                }
                else if(arista.target().equals(u)){
                    noVisitado = arista.source();
                }
                if (V.contains(noVisitado)) {
                    if (mejorArista == null || arista.dato().getWeight() < mejorArista.dato().getWeight()) {
                        mejorArista = arista;
                    }
                }
            }
        }
        return mejorArista;
    }

    //busqueda en amplitud
    @Override
    public <V, D> void bea(IUndirectedGraph<V, D> graph, java.util.function.Consumer<V> consumer) {
        Set<V> visitados = new HashSet<>();
        Queue<V> cola = new ArrayDeque<>();

        for (V v : graph.vertices()) {
            if (!visitados.contains(v)) {
                cola.add(v);

                while (!cola.isEmpty()) {
                    V x = cola.poll();
                    visitados.add(x);
                    consumer.accept(x);

                    for (var arista : graph.adyacencias(graph.construirComparable(x))) {
                        V y = arista.target();
                        if (!visitados.contains(y)) {
                            cola.add(y);
                            visitados.add(y);
                        }
                    }
                }
            }
        }
    }

}
