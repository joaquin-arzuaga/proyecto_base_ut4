package ucu.edu.aed.tda.grafo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
