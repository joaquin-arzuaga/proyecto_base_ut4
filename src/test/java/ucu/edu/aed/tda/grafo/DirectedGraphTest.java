package ucu.edu.aed.tda.grafo;

import static org.junit.jupiter.api.Assertions.*;
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
    public void testEsConexo() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));
        grafo.agregarArista("C", "A", new WeightedEdge(1.0));
        assertTrue(grafo.esConexo());
    }

    @Test
    public void testNoEsConexo() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));
        assertFalse(grafo.esConexo());
    }
}
