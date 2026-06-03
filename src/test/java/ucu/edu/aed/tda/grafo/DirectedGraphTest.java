package ucu.edu.aed.tda.grafo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;

public class DirectedGraphTest {

    @Test
    public void testAgregarVerticeNuevo() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        assertTrue(grafo.agregarVertice("A"));
    }

    @Test
    public void testAgregarVerticeDuplicado() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");

        assertFalse(grafo.agregarVertice("A"));
    }

    @Test
    public void testAgregarVerticeNull() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        assertFalse(grafo.agregarVertice(null));
    }

    @Test
    public void testBuscarVerticeExistente() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");

        String resultado = grafo.buscarVertice(grafo.construirComparable("A"));

        assertEquals("A", resultado);
    }

    @Test
    public void testAgregarAristaValida() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");

        assertTrue(grafo.agregarArista("A", "B", 10));
    }

    @Test
    public void testVaciar() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 10);

        grafo.vaciar();

        assertTrue(grafo.vertices().isEmpty());
        assertTrue(grafo.aristas().isEmpty());
    }

    @Test
    public void testTieneCiclos() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");

        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));
        grafo.agregarArista("C", "A", new WeightedEdge(1.0));

        assertTrue(grafo.tieneCiclos());
    }

    @Test
    public void testNoTieneCiclos() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");

        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));

        assertFalse(grafo.tieneCiclos());
    }


    @Test
    public void testRecorridoEnProfundidad() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");

        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("A", "C", new WeightedEdge(1.0));
        grafo.agregarArista("B", "D", new WeightedEdge(1.0));

        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        List<String> resultado = new java.util.ArrayList<>();

        algo.recorridoEnProfundidad(grafo, grafo.construirComparable("A"), v -> resultado.add(v));

        assertTrue(resultado.contains("A"));
        assertTrue(resultado.contains("B"));
        assertTrue(resultado.contains("C"));
        assertTrue(resultado.contains("D"));
        assertTrue(resultado.size() == 4);
    }

    @Test
    public void testRecorridoEnProfundidadNoVisitaDesconectado() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");

        grafo.agregarArista("A", "B", new WeightedEdge(1.0));

        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        List<String> resultado = new java.util.ArrayList<>();

        algo.recorridoEnProfundidad(grafo, grafo.construirComparable("A"), v -> resultado.add(v));

        assertTrue(resultado.contains("A"));
        assertTrue(resultado.contains("B"));
        assertFalse(resultado.contains("C"));
        assertTrue(resultado.size() == 2);
    }
}
