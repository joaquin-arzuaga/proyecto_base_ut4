package ucu.edu.aed.tda.grafo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;

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

    @Test
    public void testAgregarAristaConOrigenInexistente() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("B");

        assertFalse(grafo.agregarArista("A", "B", 10));
    }

    @Test
    public void testAgregarAristaConDestinoInexistente() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");

        assertFalse(grafo.agregarArista("A", "B", 10));
    }

    @Test
    public void testAgregarAristaDuplicada() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");

        assertTrue(grafo.agregarArista("A", "B", 10));
        assertFalse(grafo.agregarArista("A", "B", 20));
    }

    @Test
    public void testAgregarAristaConDatoNull() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");

        assertFalse(grafo.agregarArista("A", "B", null));
    }

    @Test
    public void testEliminarAristaExistente() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 10);

        assertTrue(grafo.eliminarArista(grafo.construirComparable("A"), grafo.construirComparable("B")));
        assertFalse(grafo.existeArista(grafo.construirComparable("A"), grafo.construirComparable("B")));
    }

    @Test
    public void testExisteAristaRespetaDireccion() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 10);

        assertTrue(grafo.existeArista(grafo.construirComparable("A"), grafo.construirComparable("B")));
        assertFalse(grafo.existeArista(grafo.construirComparable("B"), grafo.construirComparable("A")));
    }

    @Test
    public void testObtenerAristaExistente() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", 10);

        assertNotNull(grafo.obtenerArista(grafo.construirComparable("A"), grafo.construirComparable("B")));
        assertEquals("A", grafo.obtenerArista(grafo.construirComparable("A"), grafo.construirComparable("B")).source());
        assertEquals("B", grafo.obtenerArista(grafo.construirComparable("A"), grafo.construirComparable("B")).target());
    }

    @Test
    public void testAdyacenciasDeVerticeExistente() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");

        grafo.agregarArista("A", "B", 10);
        grafo.agregarArista("A", "C", 20);

        assertEquals(2, grafo.adyacencias(grafo.construirComparable("A")).size());
    }

    @Test
    public void testAdyacenciasDeVerticeInexistenteRetornaListaVacia() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        assertTrue(grafo.adyacencias(grafo.construirComparable("X")).isEmpty());
    }

    @Test
    public void testAdyacenciasConCriterioNullRetornaListaVacia() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        assertTrue(grafo.adyacencias(null).isEmpty());
    }

    @Test
    public void testTieneCicloConAutoArista() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarArista("A", "A", new WeightedEdge(1.0));

        assertTrue(grafo.tieneCiclos());
    }

    @Test
    public void testEliminarAristaInexistente() {
        DirectedGraph<String, Integer> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");

        assertFalse(grafo.eliminarArista(grafo.construirComparable("A"), grafo.construirComparable("B")));
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

        @Test
    public void testEsConexoGrafoVacio() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        assertTrue(grafo.esConexo());
    }

    @Test
    public void testEsConexoUnVertice() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        assertTrue(grafo.esConexo());
    }

    @Test
    public void testEsConexoVerticeAislado() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        assertFalse(grafo.esConexo());
    }

    @Test
    public void testSuccessorsConAristasSalientes() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");

        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("A", "C", new WeightedEdge(1.0));
        grafo.agregarArista("D", "A", new WeightedEdge(1.0));

        Set<String> sucesores = grafo.successors(grafo.construirComparable("A"));

        assertEquals(2, sucesores.size());
        assertTrue(sucesores.contains("B"));
        assertTrue(sucesores.contains("C"));
        assertFalse(sucesores.contains("D"));
    }

    @Test
    public void testSuccessorsSinAristasSalientes() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));

        Set<String> sucesores = grafo.successors(grafo.construirComparable("B"));

        assertNotNull(sucesores);
        assertTrue(sucesores.isEmpty());
    }

    @Test
    public void testSuccessorsVerticeInexistente() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");

        Set<String> sucesores = grafo.successors(grafo.construirComparable("X"));

        assertNotNull(sucesores);
        assertTrue(sucesores.isEmpty());
    }

    @Test
    public void testSuccessorsCriterioNull() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");

        Set<String> sucesores = grafo.successors(null);

        assertNotNull(sucesores);
        assertTrue(sucesores.isEmpty());
    }

    @Test
    public void testPredecessorsConAristasEntrantes() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");

        grafo.agregarArista("A", "C", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));
        grafo.agregarArista("C", "D", new WeightedEdge(1.0));

        Set<String> predecesores = grafo.predecessors(grafo.construirComparable("C"));

        assertEquals(2, predecesores.size());
        assertTrue(predecesores.contains("A"));
        assertTrue(predecesores.contains("B"));
        assertFalse(predecesores.contains("D"));
    }

    @Test
    public void testPredecessorsSinAristasEntrantes() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));

        Set<String> predecesores = grafo.predecessors(grafo.construirComparable("A"));

        assertNotNull(predecesores);
        assertTrue(predecesores.isEmpty());
    }

    @Test
    public void testPredecessorsVerticeInexistente() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");

        Set<String> predecesores = grafo.predecessors(grafo.construirComparable("X"));

        assertNotNull(predecesores);
        assertTrue(predecesores.isEmpty());
    }

    @Test
    public void testPredecessorsCriterioNull() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");

        Set<String> predecesores = grafo.predecessors(null);

        assertNotNull(predecesores);
        assertTrue(predecesores.isEmpty());
    }

    @Test
    public void testGradoDeSalidaUsaSuccessors() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");

        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("A", "C", new WeightedEdge(1.0));

        assertEquals(2, grafo.gradoDeSalida(grafo.construirComparable("A")));
        assertEquals(0, grafo.gradoDeSalida(grafo.construirComparable("B")));
    }

    @Test
    public void testGradoDeEntradaUsaPredecessors() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");

        grafo.agregarArista("A", "C", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));

        assertEquals(2, grafo.gradoDeEntrada(grafo.construirComparable("C")));
        assertEquals(0, grafo.gradoDeEntrada(grafo.construirComparable("A")));
    }

    @Test
    public void testObtenerTodosLosCaminos() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");

        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("B", "D", new WeightedEdge(1.0));
        grafo.agregarArista("A", "C", new WeightedEdge(1.0));
        grafo.agregarArista("C", "D", new WeightedEdge(1.0));

        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();

        List<?> caminos = algo.obtenerTodosLosCaminos(
            grafo.construirComparable("A"),
            grafo.construirComparable("D"),
            grafo);

        assertTrue(caminos.size() == 2);
    }

}
