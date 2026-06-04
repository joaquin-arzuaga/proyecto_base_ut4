package ucu.edu.aed.tda.grafo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;

public class DirectedGraphAlgorithmTests {

    private final DirectedGraphAlgorithm algorithms = new DirectedGraphAlgorithm();

    @Test
    public void testWarshall() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));

        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        IFloydWarshallResult<String> result = algo.warshall(grafo);

        assertTrue(result.connected("A", "C"));  
        assertFalse(result.connected("C", "A")); 
    }

        @Test
    public void testWarshallGrafoCompleto() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));
        grafo.agregarArista("C", "A", new WeightedEdge(1.0));
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        IFloydWarshallResult<String> result = algo.warshall(grafo);
        assertTrue(result.connected("A", "C"));
        assertTrue(result.connected("C", "A"));
        assertTrue(result.connected("B", "A"));
    }

    @Test
    public void testWarshallVerticeAislado() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        IFloydWarshallResult<String> result = algo.warshall(grafo);
        assertFalse(result.connected("A", "C"));
        assertFalse(result.connected("C", "A"));
    }

    @Test
    public void testWarshallCostos() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", new WeightedEdge(2.0));
        grafo.agregarArista("B", "C", new WeightedEdge(3.0));
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        IFloydWarshallResult<String> result = algo.warshall(grafo);
        assertEquals(5.0, result.getCost("A", "C"), 0.001);
        assertEquals(2.0, result.getCost("A", "B"), 0.001);
    }

    @Test
    public void testWarshallMismoVertice() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        IFloydWarshallResult<String> result = algo.warshall(grafo);
        assertTrue(result.connected("A", "A"));
        assertEquals(0.0, result.getCost("A", "A"), 0.001);
    }

    @Test
    public void testWarshallCaminoLargo() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarVertice("E");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));
        grafo.agregarArista("C", "D", new WeightedEdge(1.0));
        grafo.agregarArista("D", "E", new WeightedEdge(1.0));
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        IFloydWarshallResult<String> result = algo.warshall(grafo);
        assertTrue(result.connected("A", "E"));
        assertEquals(4.0, result.getCost("A", "E"), 0.001);
        assertFalse(result.connected("E", "A"));
    }

    @Test
    public void testWarshallGrafoVacio() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        IFloydWarshallResult<String> result = algo.warshall(grafo);
        assertNotNull(result);
    }

    @Test
    public void testWarshallUnVertice() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        IFloydWarshallResult<String> result = algo.warshall(grafo);
        assertTrue(result.connected("A", "A"));
        assertEquals(0.0, result.getCost("A", "A"), 0.001);
    }

    @Test
    public void testWarshallConCiclo() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));
        grafo.agregarArista("C", "A", new WeightedEdge(1.0));
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        IFloydWarshallResult<String> result = algo.warshall(grafo);
        assertTrue(result.connected("A", "C"));
        assertTrue(result.connected("C", "A"));
    }

    @Test
    public void testWarshallVerticeNoLlegaANadie() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        IFloydWarshallResult<String> result = algo.warshall(grafo);
        assertFalse(result.connected("A", "C"));
        assertFalse(result.connected("B", "C"));
    }

    @Test
    public void testClasificacionTopologica() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));

        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        List<String> resultado = algo.calcularClasificacionTopologica(grafo);

        
        assertTrue(resultado.indexOf("A") < resultado.indexOf("B"));
        assertTrue(resultado.indexOf("B") < resultado.indexOf("C"));
    }

    @Test
    public void testTopologicaMultiplesCaminos() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("A", "C", new WeightedEdge(1.0));
        grafo.agregarArista("B", "D", new WeightedEdge(1.0));
        grafo.agregarArista("C", "D", new WeightedEdge(1.0));
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        List<String> resultado = algo.calcularClasificacionTopologica(grafo);
        assertTrue(resultado.indexOf("A") < resultado.indexOf("B"));
        assertTrue(resultado.indexOf("A") < resultado.indexOf("C"));
        assertTrue(resultado.indexOf("B") < resultado.indexOf("D"));
        assertTrue(resultado.indexOf("C") < resultado.indexOf("D"));
    }

    @Test
    public void testTopologicaTodosIndependientes() {
        DirectedGraph<String, WeightedEdge> grafod = new DirectedGraph<>();
        grafod.agregarVertice("A");
        grafod.agregarVertice("B");
        grafod.agregarVertice("C");
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        List<String> resultado = algo.calcularClasificacionTopologica(grafod);
        assertEquals(3, resultado.size());
    }

    @Test
    public void testTopologicaCincoNodos() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarVertice("E");
        grafo.agregarArista("A", "C", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));
        grafo.agregarArista("C", "D", new WeightedEdge(1.0));
        grafo.agregarArista("C", "E", new WeightedEdge(1.0));
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        List<String> resultado = algo.calcularClasificacionTopologica(grafo);
        assertTrue(resultado.indexOf("A") < resultado.indexOf("C"));
        assertTrue(resultado.indexOf("B") < resultado.indexOf("C"));
        assertTrue(resultado.indexOf("C") < resultado.indexOf("D"));
        assertTrue(resultado.indexOf("C") < resultado.indexOf("E"));
    }

    @Test
    public void testTopologicaCadenaLarga() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarVertice("E");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));
        grafo.agregarArista("C", "D", new WeightedEdge(1.0));
        grafo.agregarArista("D", "E", new WeightedEdge(1.0));
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        List<String> resultado = algo.calcularClasificacionTopologica(grafo);
        assertEquals(5, resultado.size());
        assertTrue(resultado.indexOf("A") < resultado.indexOf("E"));
    }

    @Test
    public void testTopologicaGrafoVacio() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        List<String> resultado = algo.calcularClasificacionTopologica(grafo);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testTopologicaUnVertice() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        List<String> resultado = algo.calcularClasificacionTopologica(grafo);
        assertEquals(1, resultado.size());
        assertEquals("A", resultado.get(0));
    }

    @Test
    public void testTopologicaConCiclo() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("B", "C", new WeightedEdge(1.0));
        grafo.agregarArista("C", "A", new WeightedEdge(1.0));
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        List<String> resultado = algo.calcularClasificacionTopologica(grafo);
        assertTrue(resultado.size() < 3);
    }

    @Test
    public void testTopologicaRaizUnica() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarArista("A", "B", new WeightedEdge(1.0));
        grafo.agregarArista("A", "C", new WeightedEdge(1.0));
        grafo.agregarArista("A", "D", new WeightedEdge(1.0));
        DirectedGraphAlgorithm algo = new DirectedGraphAlgorithm();
        List<String> resultado = algo.calcularClasificacionTopologica(grafo);
        assertEquals("A", resultado.get(0));
    }

        @Test
    void obtenerExcentricidadCalculaMayorDistanciaMinimaDesdeUnVertice() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C", "D"));

        grafo.agregarArista("A", "B", new WeightedEdge(2));
        grafo.agregarArista("A", "C", new WeightedEdge(5));
        grafo.agregarArista("B", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "D", new WeightedEdge(3));
        grafo.agregarArista("B", "D", new WeightedEdge(10));

        double excentricidad = algorithms.obtenerExcentricidad(
                grafo,
                grafo.construirComparable("A")
        );

        // Caminos mínimos desde A:
        // A -> A = 0
        // A -> B = 2
        // A -> C = 3  (A -> B -> C)
        // A -> D = 6  (A -> B -> C -> D)
        assertEquals(6.0, excentricidad, 0.0001);
    }

    @Test
    void obtenerExcentricidadDeVerticeSinLlegarATodosRetornaInfinito() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C"));

        grafo.agregarArista("A", "B", new WeightedEdge(4));

        double excentricidad = algorithms.obtenerExcentricidad(
                grafo,
                grafo.construirComparable("A")
        );

        // Desde A no se llega a C, entonces la excentricidad queda infinita.
        assertEquals(Double.POSITIVE_INFINITY, excentricidad);
    }

    @Test
    void obtenerExcentricidadConVerticeInexistenteLanzaExcepcion() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B"));
        grafo.agregarArista("A", "B", new WeightedEdge(1));

        assertThrows(
                IllegalArgumentException.class,
                () -> algorithms.obtenerExcentricidad(
                        grafo,
                        grafo.construirComparable("Z")
                )
        );
    }

    @Test
    void obtenerCentroGrafoConUnSoloVerticeDevuelveEseVertice() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertice("A");

        String centro = algorithms.obtenerCentroGrafo(grafo);

        assertEquals("A", centro);
        assertEquals(
                0.0,
                algorithms.obtenerExcentricidad(grafo, grafo.construirComparable("A")),
                0.0001
        );
    }

    @Test
    void obtenerCentroGrafoVacioDevuelveNull() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();

        String centro = algorithms.obtenerCentroGrafo(grafo);

        assertNull(centro);
    }

    @Test
    void obtenerCentroGrafoDevuelveVerticeConMenorExcentricidad() {
        DirectedGraph<String, WeightedEdge> grafo = new DirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C", "D"));

        /*
         Grafo :

         Desde A:
         A->B = 1, A->C = 2, A->D = 3  => exc(A) = 3

         Desde B:
         B->C = 1, B->D = 2, B->A = 3  => exc(B) = 3

         Desde C:
         C->D = 1, C->A = 2, C->B = 3  => exc(C) = 3

         Desde D:
         D->A = 1, D->B = 2, D->C = 3  => exc(D) = 3

        */

        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "D", new WeightedEdge(1));
        grafo.agregarArista("D", "A", new WeightedEdge(1));

        grafo.agregarArista("B", "A", new WeightedEdge(1));
        grafo.agregarArista("B", "D", new WeightedEdge(1));

        String centro = algorithms.obtenerCentroGrafo(grafo);

        assertEquals("B", centro);
    }
}
