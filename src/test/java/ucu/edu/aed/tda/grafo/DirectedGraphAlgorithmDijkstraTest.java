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
                () -> algorithms.dijkstra(grafo.construirComparable("Z"), grafo));
    }

    @Test
    void dijkstraLanzaExcepcionSiExisteUnaAristaNegativaAlcanzable() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B"));
        grafo.agregarArista("A", "B", new WeightedEdge(-5));

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithms.dijkstra(grafo.construirComparable("A"), grafo));
    }

    @Test
    void dijkstraConUnSoloVertice() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");

        IDijkstraResult<String> result = algorithms.dijkstra(grafo.construirComparable("A"), grafo);

        assertEquals(0.0, result.getCost("A"), 0.0001);
        assertEquals(List.of("A"), result.getPath("A"));
    }

    @Test
    void dijkstraConGrafoVacioLanzaExcepcion() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithms.dijkstra(grafo.construirComparable("A"), grafo));
    }

    @Test
    void dijkstraRespetaDireccionDeAristas() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B"));

        grafo.agregarArista("A", "B", new WeightedEdge(5));

        IDijkstraResult<String> resultDesdeA = algorithms.dijkstra(grafo.construirComparable("A"), grafo);
        assertEquals(5.0, resultDesdeA.getCost("B"), 0.0001);
        assertEquals(List.of("A", "B"), resultDesdeA.getPath("B"));

        IDijkstraResult<String> resultDesdeB = algorithms.dijkstra(grafo.construirComparable("B"), grafo);
        assertEquals(Double.POSITIVE_INFINITY, resultDesdeB.getCost("A"));
        assertTrue(resultDesdeB.getPath("A").isEmpty());
    }

    @Test
    void dijkstraSoportaAristasConPesoCero() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));

        grafo.agregarArista("A", "B", new WeightedEdge(0));
        grafo.agregarArista("B", "C", new WeightedEdge(0));

        IDijkstraResult<String> result = algorithms.dijkstra(grafo.construirComparable("A"), grafo);

        assertEquals(0.0, result.getCost("B"), 0.0001);
        assertEquals(0.0, result.getCost("C"), 0.0001);
        assertEquals(List.of("A", "B"), result.getPath("B"));
        assertEquals(List.of("A", "B", "C"), result.getPath("C"));
    }

    @Test
    void dijkstraMantieneCaminoDirectoSiEsMasBarato() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));

        grafo.agregarArista("A", "C", new WeightedEdge(2));
        grafo.agregarArista("A", "B", new WeightedEdge(5));
        grafo.agregarArista("B", "C", new WeightedEdge(5));

        IDijkstraResult<String> result = algorithms.dijkstra(grafo.construirComparable("A"), grafo);

        assertEquals(2.0, result.getCost("C"), 0.0001);
        assertEquals(List.of("A", "C"), result.getPath("C"));
    }

    @Test
    void dijkstraResultadoParaVerticeInexistenteRetornaInfinitoYCaminoVacio() {
        DirectedGraph<String, WeightedEdge> grafo = crearGrafoBase();

        IDijkstraResult<String> result = algorithms.dijkstra(grafo.construirComparable("A"), grafo);

        assertEquals(Double.POSITIVE_INFINITY, result.getCost("Z"));
        assertTrue(result.getPath("Z").isEmpty());
    }

    @Test
    void dijkstraNoLanzaExcepcionPorAristaNegativaNoAlcanzable() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "X", "Y"));

        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("X", "Y", new WeightedEdge(-5));

        IDijkstraResult<String> result = algorithms.dijkstra(grafo.construirComparable("A"), grafo);

        assertEquals(1.0, result.getCost("B"), 0.0001);
        assertEquals(Double.POSITIVE_INFINITY, result.getCost("X"));
        assertEquals(Double.POSITIVE_INFINITY, result.getCost("Y"));
        assertTrue(result.getPath("X").isEmpty());
        assertTrue(result.getPath("Y").isEmpty());
    }

    @Test
    void dijkstraManejaCiclosConPesosPositivos() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C", "D"));

        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "A", new WeightedEdge(1));
        grafo.agregarArista("C", "D", new WeightedEdge(2));

        IDijkstraResult<String> result = algorithms.dijkstra(grafo.construirComparable("A"), grafo);

        assertEquals(0.0, result.getCost("A"), 0.0001);
        assertEquals(1.0, result.getCost("B"), 0.0001);
        assertEquals(2.0, result.getCost("C"), 0.0001);
        assertEquals(4.0, result.getCost("D"), 0.0001);
        assertEquals(List.of("A", "B", "C", "D"), result.getPath("D"));
    }

    @Test
    void dijkstraMantieneCostoCeroAlOrigenAunqueExistaAutoAristaPositiva() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");

        grafo.agregarArista("A", "A", new WeightedEdge(10));

        IDijkstraResult<String> result = algorithms.dijkstra(grafo.construirComparable("A"), grafo);

        assertEquals(0.0, result.getCost("A"), 0.0001);
        assertEquals(List.of("A"), result.getPath("A"));
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
