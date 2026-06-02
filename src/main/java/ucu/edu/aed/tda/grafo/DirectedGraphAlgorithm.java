package ucu.edu.aed.tda.grafo;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IDijkstraResult;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;
import ucu.edu.aed.tda.grafo.model.result.Path;

public class DirectedGraphAlgorithm implements IDirectedGraphAlgorithms {

    @Override
    public <V, D extends WeightedEdge> IDijkstraResult<V> dijkstra(Comparable<V> source, IDirectedIGraph<V, D> grafo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'dijkstra'");
    }

    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> floyd(IDirectedIGraph<V, D> grafo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'floyd'");
    }

    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> warshall(IDirectedIGraph<V, D> grafo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'warshall'");
    }

    @Override
    public <V, D extends WeightedEdge> V obtenerCentroGrafo(IDirectedIGraph<V, D> grafo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerCentroGrafo'");
    }

    @Override
    public <V, D extends WeightedEdge> double obtenerExcentricidad(IDirectedIGraph<V, D> grafo,
            Comparable<V> vertexCriteria) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerExcentricidad'");
    }

    @Override
    public <V, D extends WeightedEdge> List<Path<V>> obtenerTodosLosCaminos(Comparable<V> source, Comparable<V> target,
            IGraph<V, D> grafo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerTodosLosCaminos'");
    }

    @Override
    public <V, D> void recorridoEnProfundidad(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer) {
        Deque<V> vecinos = new LinkedList<V>();
        HashSet<V> visitados = new HashSet<V>();

        visitados.add(grafo.buscarVertice(sourceCriteria));
        vecinos.add(grafo.buscarVertice(sourceCriteria));
        while (vecinos.size() != 0)
        {
            V vertice = vecinos.pop();
            for (Edge<V, D> ver : grafo.adyacencias(grafo.construirComparable(vertice))) {
                if (!visitados.contains(ver.target()))
                {
                    vecinos.add(ver.target());
                }
            }
            consumer.accept(vertice);
            visitados.add(vertice);
        }
    }

    @Override
    public <V, D> void recorridoEnAmplitud(IGraph<V, D> grafo, Comparable<V> sourceCriteria, Consumer<V> consumer) {
        Queue<V> vecinos = new LinkedList<V>();
        HashSet<V> visitados = new HashSet<V>();

        visitados.add(grafo.buscarVertice(sourceCriteria));
        vecinos.add(grafo.buscarVertice(sourceCriteria));
        while (vecinos.size() != 0)
        {
            V vertice = vecinos.poll();
            for (Edge<V, D> ver : grafo.adyacencias(grafo.construirComparable(vertice))) {
                if (!visitados.contains(ver.target()))
                {
                    vecinos.add(ver.target());
                }
            }
            consumer.accept(vertice);
            visitados.add(vertice);
        }
    }

    @Override
    public <V, D> List<V> calcularClasificacionTopologica(IDirectedIGraph<V, D> grafo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcularClasificacionTopologica'");
    }

}
