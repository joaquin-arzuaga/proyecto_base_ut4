package ucu.edu.aed.tda.grafo;

import org.junit.jupiter.api.Test;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectedGraphAlgorithmFloydTest {

    private final DirectedGraphAlgorithm algorithms = new DirectedGraphAlgorithm();

    @Test
    void floydCalculaCostosYCaminosMinimosEntreTodosLosPares() {
        DirectedGraph<String, WeightedEdge> grafo = crearGrafoBase();

        IFloydWarshallResult<String> result = algorithms.floyd(grafo);

        assertEquals(0.0, result.getCost("A", "A"), 0.0001);
        assertEquals(3.0, result.getCost("A", "B"), 0.0001); // A -> C -> B
        assertEquals(1.0, result.getCost("A", "C"), 0.0001); // A -> C
        assertEquals(4.0, result.getCost("A", "D"), 0.0001); // A -> C -> B -> D
        assertEquals(7.0, result.getCost("A", "E"), 0.0001); // A -> C -> B -> D -> E
        assertEquals(6.0, result.getCost("C", "E"), 0.0001); // C -> B -> D -> E

        assertEquals(List.of("A"), result.getPath("A", "A"));
        assertEquals(List.of("A", "C", "B"), result.getPath("A", "B"));
        assertEquals(List.of("A", "C", "B", "D"), result.getPath("A", "D"));
        assertEquals(List.of("A", "C", "B", "D", "E"), result.getPath("A", "E"));
        assertEquals(List.of("C", "B", "D", "E"), result.getPath("C", "E"));
    }

    @Test
    void floydReportaConectividadCorrectamente() {
        DirectedGraph<String, WeightedEdge> grafo = crearGrafoBase();
        grafo.agregarVertice("F");

        IFloydWarshallResult<String> result = algorithms.floyd(grafo);

        assertTrue(result.connected("A", "E"));
        assertTrue(result.connected("A", "A"));
        assertFalse(result.connected("A", "F"));
        assertFalse(result.connected("F", "A"));

        assertEquals(Double.POSITIVE_INFINITY, result.getCost("A", "F"));
        assertEquals(Double.POSITIVE_INFINITY, result.getCost("F", "A"));
        assertTrue(result.getPath("A", "F").isEmpty());
        assertTrue(result.getPath("F", "A").isEmpty());
    }

    @Test
    void floydPuedeMejorarUnCaminoDirectoUsandoVerticesIntermedios() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));
        grafo.agregarArista("A", "C", new WeightedEdge(20));
        grafo.agregarArista("A", "B", new WeightedEdge(4));
        grafo.agregarArista("B", "C", new WeightedEdge(3));

        IFloydWarshallResult<String> result = algorithms.floyd(grafo);

        assertEquals(7.0, result.getCost("A", "C"), 0.0001);
        assertEquals(List.of("A", "B", "C"), result.getPath("A", "C"));
    }

    @Test
    void floydConGrafoVacioNoFalla() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        IFloydWarshallResult<String> result = algorithms.floyd(grafo);

        assertEquals(Double.POSITIVE_INFINITY, result.getCost("A", "B"));
        assertFalse(result.connected("A", "B"));
        assertTrue(result.getPath("A", "B").isEmpty());
    }

    @Test
    void floydConUnSoloVertice() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");

        IFloydWarshallResult<String> result = algorithms.floyd(grafo);

        assertEquals(0.0, result.getCost("A", "A"), 0.0001);
        assertTrue(result.connected("A", "A"));
        assertEquals(List.of("A"), result.getPath("A", "A"));
    }

    @Test
    void floydRespetaDireccionDeAristas() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B"));

        grafo.agregarArista("A", "B", new WeightedEdge(5));

        IFloydWarshallResult<String> result = algorithms.floyd(grafo);

        assertEquals(5.0, result.getCost("A", "B"), 0.0001);
        assertEquals(List.of("A", "B"), result.getPath("A", "B"));

        assertEquals(Double.POSITIVE_INFINITY, result.getCost("B", "A"));
        assertFalse(result.connected("B", "A"));
        assertTrue(result.getPath("B", "A").isEmpty());
    }

    @Test
    void floydSoportaAristasConPesoCero() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));

        grafo.agregarArista("A", "B", new WeightedEdge(0));
        grafo.agregarArista("B", "C", new WeightedEdge(0));

        IFloydWarshallResult<String> result = algorithms.floyd(grafo);

        assertEquals(0.0, result.getCost("A", "C"), 0.0001);
        assertEquals(List.of("A", "B", "C"), result.getPath("A", "C"));
    }

    @Test
    void floydSoportaPesosNegativosSinCicloNegativo() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));

        grafo.agregarArista("A", "B", new WeightedEdge(4));
        grafo.agregarArista("B", "C", new WeightedEdge(-2));
        grafo.agregarArista("A", "C", new WeightedEdge(10));

        IFloydWarshallResult<String> result = algorithms.floyd(grafo);

        assertEquals(2.0, result.getCost("A", "C"), 0.0001);
        assertEquals(List.of("A", "B", "C"), result.getPath("A", "C"));
    }

    @Test
    void floydMantieneCostoCeroHaciaElMismoVerticeAunqueExistaAutoAristaPositiva() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");

        grafo.agregarArista("A", "A", new WeightedEdge(5));

        IFloydWarshallResult<String> result = algorithms.floyd(grafo);

        assertEquals(0.0, result.getCost("A", "A"), 0.0001);
        assertEquals(List.of("A"), result.getPath("A", "A"));
    }

    @Test
    void floydConVerticesInexistentesRetornaInfinitoYCaminoVacio() {
        DirectedGraph<String, WeightedEdge> grafo = crearGrafoBase();

        IFloydWarshallResult<String> result = algorithms.floyd(grafo);

        assertEquals(Double.POSITIVE_INFINITY, result.getCost("X", "Y"));
        assertFalse(result.connected("X", "Y"));
        assertTrue(result.getPath("X", "Y").isEmpty());
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
