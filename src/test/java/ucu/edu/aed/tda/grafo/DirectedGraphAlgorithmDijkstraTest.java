package ucu.edu.aed.tda.grafo;

import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IDijkstraResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphAlgorithmDijkstraTest {

    private final DirectedGraphAlgorithm algorithms = new DirectedGraphAlgorithm();

    @Test
    void dijkstraCalculaCostosYCaminosMinimosDesdeElOrigen() {
        DirectedGraph<String, WeightedEdge> grafo = crearGrafoBase();

        IDijkstraResult<String> result = algorithms.dijkstra(grafo.construirComparable("A"), grafo);

        assertEquals(0.0, result.getCost("A"), 0.0001);
        assertEquals(3.0, result.getCost("B"), 0.0001); // A -> C -> B
        assertEquals(1.0, result.getCost("C"), 0.0001); // A -> C
        assertEquals(4.0, result.getCost("D"), 0.0001); // A -> C -> B -> D
        assertEquals(7.0, result.getCost("E"), 0.0001); // A -> C -> B -> D -> E

        assertEquals(List.of("A"), result.getPath("A"));
        assertEquals(List.of("A", "C", "B"), result.getPath("B"));
        assertEquals(List.of("A", "C"), result.getPath("C"));
        assertEquals(List.of("A", "C", "B", "D"), result.getPath("D"));
        assertEquals(List.of("A", "C", "B", "D", "E"), result.getPath("E"));
    }

    @Test
    void dijkstraDevuelveInfinitoYCaminoVacioParaVerticesNoAlcanzables() {
        DirectedGraph<String, WeightedEdge> grafo = crearGrafoBase();
        grafo.agregarVertice("F");

        IDijkstraResult<String> result = algorithms.dijkstra(grafo.construirComparable("A"), grafo);

        assertEquals(Double.POSITIVE_INFINITY, result.getCost("F"));
        assertTrue(result.getPath("F").isEmpty());
    }

    @Test
    void dijkstraLanzaExcepcionSiElOrigenNoExiste() {
        DirectedGraph<String, WeightedEdge> grafo = crearGrafoBase();

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithms.dijkstra(grafo.construirComparable("Z"), grafo)
        );
    }

    @Test
    void dijkstraLanzaExcepcionSiExisteUnaAristaNegativaAlcanzable() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B"));
        grafo.agregarArista("A", "B", new WeightedEdge(-5));

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithms.dijkstra(grafo.construirComparable("A"), grafo)
        );
    }

    private DirectedGraph<String, WeightedEdge> crearGrafoBase() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C", "D", "E"));

        grafo.agregarArista("A", "B", new WeightedEdge(10));
        grafo.agregarArista("A", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "B", new WeightedEdge(2));
        grafo.agregarArista("B", "D", new WeightedEdge(1));
        grafo.agregarArista("C", "D", new WeightedEdge(8));
        grafo.agregarArista("D", "E", new WeightedEdge(3));

        return grafo;
    }
}
