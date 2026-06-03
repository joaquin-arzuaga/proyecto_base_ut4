package ucu.edu.aed.tda.grafo;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IDijkstraResult;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;
import ucu.edu.aed.tda.grafo.model.result.Path;
/* 
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
    Map<V, Map<V, Double>> costs = new HashMap<>();
    Map<V, Map<V, V>>      next  = new HashMap<>();

    for (V v : grafo.vertices()) {
        costs.put(v, new HashMap<>());
        next.put(v, new HashMap<>());
        costs.get(v).put(v, 0.0);
        next.get(v).put(v, v);
    }


    for (Edge<V, D> arista : grafo.aristas()) {
        V s = arista.source();
        V t = arista.target();
        costs.get(s).put(t, arista.dato().getWeight());
        next.get(s).put(t, t);
    }

    List<V> vertices = new ArrayList<>(grafo.vertices());
    for (V k : vertices) {
        for (V i : vertices) {
            for (V j : vertices) {
                Double ik = costs.get(i).get(k);
                Double kj = costs.get(k).get(j);
                if (ik != null && kj != null) {
                    Double ij = costs.get(i).get(j);
                    double newCost = ik + kj;
                    if (ij == null || newCost < ij) {
                        costs.get(i).put(j, newCost);
                        next.get(i).put(j, next.get(i).get(k));
                    }
                }
            }
        }
    }

    return new FloydWarshallResult<>(costs, next);
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
        HashMap<V, Integer> aristasEntrantes = new HashMap<V, Integer>();
        Queue<V> grado = new LinkedList<V>();
        List<V> result = new LinkedList<V>();
        for (V vertice : grafo.vertices()) {
            aristasEntrantes.put(vertice, 0);
        }
        for (Edge <V, D> arista : grafo.aristas()) {
            V vertice = arista.target();
            aristasEntrantes.put(vertice, aristasEntrantes.getOrDefault(vertice, 0) + 1);
        }
        for (V vertice : aristasEntrantes.keySet()) {
            if (aristasEntrantes.get(vertice) == 0) {
                grado.add(vertice);
            }
        }
        while (!grado.isEmpty())
        {
            V sacada = grado.poll();
            aristasEntrantes.remove(sacada);
            result.add(sacada);
            for (Edge <V, D> arist : grafo.adyacencias(grafo.construirComparable(sacada))) {
                V ver = arist.target();
                aristasEntrantes.put(ver, aristasEntrantes.getOrDefault(ver, 0) - 1);
            }
            for (V vertice : aristasEntrantes.keySet()) {
                if (aristasEntrantes.get(vertice) == 0) {
                    grado.add(vertice);
                }
            }
        }
        return result;
    }
}
*/