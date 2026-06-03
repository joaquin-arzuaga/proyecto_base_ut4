package ucu.edu.aed.tda.grafo;

import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import ucu.edu.aed.tda.grafo.model.IGraph;
import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IDijkstraResult;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;
import ucu.edu.aed.tda.grafo.model.result.Path;
import ucu.edu.aed.tda.grafo.model.result.FloydWarshallResult;
import ucu.edu.aed.tda.grafo.model.result.DijkstraResult;


public class DirectedGraphAlgorithm implements IDirectedGraphAlgorithms {

    @Override
    public <V, D extends WeightedEdge> IDijkstraResult<V> dijkstra(Comparable<V> source, IDirectedIGraph<V, D> grafo) {
        V sourceVertex = grafo.buscarVertice(source);
        if (sourceVertex == null) {
            throw new IllegalArgumentException("El vertice origen no existe en el grafo");
        }

        Map<V, Double> distancias = new HashMap<>();
        Map<V, V> anteriores = new HashMap<>();
        Set<V> visitados = new HashSet<>();

        for (V vertice : grafo.vertices()) {
            distancias.put(vertice, Double.POSITIVE_INFINITY);
        }
        distancias.put(sourceVertex, 0.0);

        PriorityQueue<V> pendientes = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));
        pendientes.add(sourceVertex);

        while (!pendientes.isEmpty()) {
            V actual = pendientes.poll();
            if (!visitados.add(actual)) {
                continue;
            }

            for (Edge<V, D> arista : grafo.adyacencias(grafo.construirComparable(actual))) {
                double peso = arista.dato().getWeight();
                if (peso < 0) {
                    throw new IllegalArgumentException("Dijkstra no admite aristas con peso negativo");
                }

                V vecino = arista.target();
                double nuevaDistancia = distancias.get(actual) + peso;
                if (nuevaDistancia < distancias.get(vecino)) {
                    distancias.put(vecino, nuevaDistancia);
                    anteriores.put(vecino, actual);
                    pendientes.add(vecino);
                }
            }
        }

        return new DijkstraResult<>(sourceVertex, distancias, anteriores);
    }

    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> floyd(IDirectedIGraph<V, D> grafo) {
        Map<V, Map<V, Double>> costos = inicializarCostos(grafo.vertices());
        Map<V, Map<V, V>> siguientes = inicializarSiguientes(grafo.vertices());

        for (V vertice : grafo.vertices()) {
            costos.get(vertice).put(vertice, 0.0);
            siguientes.get(vertice).put(vertice, vertice);
        }

        for (Edge<V, D> arista : grafo.aristas()) {
            double peso = arista.dato().getWeight();
            V origen = arista.source();
            V destino = arista.target();
            if (peso < costos.get(origen).get(destino)) {
                costos.get(origen).put(destino, peso);
                siguientes.get(origen).put(destino, destino);
            }
        }

        for (V intermedio : grafo.vertices()) {
            for (V origen : grafo.vertices()) {
                for (V destino : grafo.vertices()) {
                    double costoOrigenIntermedio = costos.get(origen).get(intermedio);
                    double costoIntermedioDestino = costos.get(intermedio).get(destino);

                    if (Double.isInfinite(costoOrigenIntermedio) || Double.isInfinite(costoIntermedioDestino)) {
                        continue;
                    }

                    double nuevoCosto = costoOrigenIntermedio + costoIntermedioDestino;
                    if (nuevoCosto < costos.get(origen).get(destino)) {
                        costos.get(origen).put(destino, nuevoCosto);
                        siguientes.get(origen).put(destino, siguientes.get(origen).get(intermedio));
                    }
                }
            }
        }

        return new FloydWarshallResult<>(costos, siguientes);
    }

    @Override
    public <V, D extends WeightedEdge> IFloydWarshallResult<V> warshall(IDirectedIGraph<V, D> grafo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'warshall'");
    }

    private <V> Map<V, Map<V, Double>> inicializarCostos(Set<V> vertices) {
        Map<V, Map<V, Double>> costos = new HashMap<>();
        for (V origen : vertices) {
            Map<V, Double> fila = new HashMap<>();
            for (V destino : vertices) {
                fila.put(destino, Double.POSITIVE_INFINITY);
            }
            costos.put(origen, fila);
        }
        return costos;
    }

    private <V> Map<V, Map<V, V>> inicializarSiguientes(Set<V> vertices) {
        Map<V, Map<V, V>> siguientes = new HashMap<>();
        for (V origen : vertices) {
            Map<V, V> fila = new HashMap<>();
            for (V destino : vertices) {
                fila.put(destino, null);
            }
            siguientes.put(origen, fila);
        }
        return siguientes;
    }

    @Override
    public <V, D extends WeightedEdge> V obtenerCentroGrafo(IDirectedIGraph<V, D> grafo) {
        V centro = null;
        double menorExcentricidad = Double.POSITIVE_INFINITY;

        for (V vertice : grafo.vertices()) {
            double excentricidad = obtenerExcentricidad(grafo, grafo.construirComparable(vertice));
            if (excentricidad < menorExcentricidad) {
                menorExcentricidad = excentricidad;
                centro = vertice;
            }
        }

        return centro;
    }

    @Override
    public <V, D extends WeightedEdge> double obtenerExcentricidad(IDirectedIGraph<V, D> grafo, Comparable<V> vertexCriteria) {
        V vertice = grafo.buscarVertice(vertexCriteria);
        if (vertice == null) {
            throw new IllegalArgumentException("El vertice no existe en el grafo");
        }

        IFloydWarshallResult<V> resultado = floyd(grafo);
        double excentricidad = 0.0;
        for (V destino : grafo.vertices()) {
            excentricidad = Math.max(excentricidad, resultado.getCost(vertice, destino));
        }
        return excentricidad;
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
