package ucu.edu.aed.tda.grafo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;
import ucu.edu.aed.tda.grafo.model.result.IFloydWarshallResult;

public class DirectedGraphAlgorithmTests {
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
}
