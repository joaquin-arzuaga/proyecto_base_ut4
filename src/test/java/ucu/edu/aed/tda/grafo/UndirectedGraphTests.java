package ucu.edu.aed.tda.grafo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;

public class UndirectedGraphTests {

    private UndirectedGraph<String, WeightedEdge> crearGrafoConCiclo() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarVertice("C");
        g.agregarArista("A", "B", new WeightedEdge(1));
        g.agregarArista("B", "C", new WeightedEdge(1));
        g.agregarArista("C", "A", new WeightedEdge(1));
        return g;
    }

    private UndirectedGraph<String, WeightedEdge> crearGrafoSinCiclo() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarVertice("C");
        g.agregarArista("A", "B", new WeightedEdge(1));
        g.agregarArista("B", "C", new WeightedEdge(1));
        return g;
    }

    @Test
    public void agregarVertice() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        assertTrue(g.agregarVertice("A"));
        assertFalse(g.agregarVertice("A"));
    }

    @Test
    public void buscarVertice() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        g.agregarVertice("A");

        assertEquals("A", g.buscarVertice(g.construirComparable("A")));
        assertNull(g.buscarVertice(g.construirComparable("Z")));
    }

    @Test
    public void agregarAristaDuplicadaInvertida() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        g.agregarVertice("A");
        g.agregarVertice("B");

        assertTrue(g.agregarArista("A", "B", new WeightedEdge(1)));
        assertFalse(g.agregarArista("B", "A", new WeightedEdge(1)));
    }

    @Test
    public void eliminarAristaInexistente() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        g.agregarVertice("A");
        g.agregarVertice("B");

        assertFalse(g.eliminarArista(g.construirComparable("A"), g.construirComparable("B")));
    }

    @Test
    public void removerVertice() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarArista("A", "B", new WeightedEdge(1));

        assertTrue(g.removerVertice(g.construirComparable("A")));
        assertFalse(g.vertices().contains("A"));
        assertTrue(g.aristas().isEmpty());
    }

    @Test
    public void verticesEsCopia() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        g.agregarVertice("A");

        g.vertices().add("B");

        assertFalse(g.vertices().contains("B"));
    }

    @Test
    public void existeArista() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarArista("A", "B", new WeightedEdge(1));

        assertTrue(g.existeArista(g.construirComparable("A"), g.construirComparable("B")));
        assertTrue(g.existeArista(g.construirComparable("B"), g.construirComparable("A")));
        assertFalse(g.existeArista(g.construirComparable("A"), g.construirComparable("C")));
    }

    @Test
    public void obtenerArista() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarArista("A", "B", new WeightedEdge(5));

        Edge<String, WeightedEdge> arista = g.obtenerArista(g.construirComparable("A"), g.construirComparable("B"));

        assertNotNull(arista);
        assertEquals(5.0, arista.dato().getWeight());
    }

    @Test
    public void adyacenciasVerticeInexistente() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        assertTrue(g.adyacencias(g.construirComparable("Z")).isEmpty());
    }

    @Test
    public void esConexoFalse() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarVertice("C");
        g.agregarArista("A", "B", new WeightedEdge(1));

        assertFalse(g.esConexo());
    }

    @Test
    public void esConexoTrue() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarArista("A", "B", new WeightedEdge(1));

        assertTrue(g.esConexo());
    }

    @Test
    public void vaciar() {
        UndirectedGraph<String, WeightedEdge> g = new UndirectedGraph<>();
        g.agregarVertice("A");
        g.agregarVertice("B");
        g.agregarArista("A", "B", new WeightedEdge(1));

        g.vaciar();

        assertTrue(g.vertices().isEmpty());
        assertTrue(g.aristas().isEmpty());
    }

    @Test
    public void tieneCiclosConCiclo() {
        UndirectedGraph<String, WeightedEdge> g = crearGrafoConCiclo();
        assertTrue(g.tieneCiclos());
    }

    @Test
    public void tieneCiclosSinCiclo() {
        UndirectedGraph<String, WeightedEdge> g = crearGrafoSinCiclo();
        assertFalse(g.tieneCiclos());
    }

}