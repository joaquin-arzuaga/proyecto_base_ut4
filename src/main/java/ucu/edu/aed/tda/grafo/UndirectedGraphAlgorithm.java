package ucu.edu.aed.tda.grafo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

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
        return null;
    }

    //minima arista
    @Override
    public <V, D extends WeightedEdge> Edge<V, D> searchMinEdge(IUndirectedGraph<V, D> graph, Collection<V> U, Collection<V> V) {
        return null;
    }

    //busqueda en amplitud
    @Override
    public <V, D> void bea(IUndirectedGraph<V, D> graph, java.util.function.Consumer<V> consumer) {
    }

}
