package ucu.edu.aed.tda.grafo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ucu.edu.aed.tda.grafo.model.edge.Edge;
import ucu.edu.aed.tda.grafo.model.edge.WeightedEdge;

public class UndirectedGraphAlgorithmsTests {

    private UndirectedGraph<String, WeightedEdge> crearGrafoBase() {
        UndirectedGraph<String, WeightedEdge> grafo = new UndirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C", "D", "E"));

        grafo.agregarArista("A", "B", new WeightedEdge(10));
        grafo.agregarArista("A", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "B", new WeightedEdge(2));
        grafo.agregarArista("B", "D", new WeightedEdge(1));
        grafo.agregarArista("C", "D", new WeightedEdge(8));
        grafo.agregarArista("D", "E", new WeightedEdge(3));

        return grafo;
    }

    @Test
    public void kruskalRecievesEmptyGraph() {
        UndirectedGraph<String, WeightedEdge> grafoVacio = new UndirectedGraph<>();
        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();
        IUndirectedGraph<String, WeightedEdge> kruskalVacio = algo.kruskal(grafoVacio);
        assertNotNull(kruskalVacio);
        assertTrue(kruskalVacio.aristas().isEmpty());
        assertTrue(kruskalVacio.vertices().isEmpty());
    }

    @Test
    public void kruskalRecievesGraphWithOneNode() {
        UndirectedGraph<String, WeightedEdge> grafoConUnNodo = new UndirectedGraph<>();
        grafoConUnNodo.agregarVertice("A");
        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();
        IUndirectedGraph<String, WeightedEdge> kruskal = algo.kruskal(grafoConUnNodo);
        assertTrue(kruskal.aristas().isEmpty());
        assertTrue(kruskal.vertices().size() == 1);
        assertTrue(kruskal.vertices().contains("A"));
    }

    @Test
    public void kruskalNormal() {
        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();
        IUndirectedGraph<String, WeightedEdge> kruskal = algo.kruskal(crearGrafoBase());
        double peso = 0;
        for (Edge<String, WeightedEdge> edge : kruskal.aristas()) {
            peso += edge.dato().getWeight();
        }
        assertTrue(kruskal.aristas().size() == kruskal.vertices().size() - 1); // Verifico que es un arbol
        assertEquals(peso, 7); // Es de costo minimo
    }

    @Test
    public void kruskalArbolDesconectado() {
        UndirectedGraph<String, WeightedEdge> grafo = new UndirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C", "D"));

        grafo.agregarArista("A", "B", new WeightedEdge(10));
        grafo.agregarArista("C", "D", new WeightedEdge(1));

        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();
        IUndirectedGraph<String, WeightedEdge> kruskal = algo.kruskal(grafo);

        assertEquals(kruskal.vertices().size(), 4);
        assertEquals(kruskal.aristas().size(), 2);
    }

    @Test
    public void primArbolVacio() {
        UndirectedGraph<String, WeightedEdge> grafoVacio = new UndirectedGraph<>();
        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();
        IUndirectedGraph<String, WeightedEdge> primVacio = algo.prim(grafoVacio, null);
        assertNotNull(primVacio);
        assertTrue(primVacio.aristas().isEmpty());
        assertTrue(primVacio.vertices().isEmpty());
    }

    @Test
    public void primRecievesGraphWithOneNode() {
        UndirectedGraph<String, WeightedEdge> grafoConUnNodo = new UndirectedGraph<>();
        grafoConUnNodo.agregarVertice("A");
        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();
        IUndirectedGraph<String, WeightedEdge> prim = algo.prim(grafoConUnNodo, "A");
        assertTrue(prim.aristas().isEmpty());
        assertTrue(prim.vertices().size() == 1);
        assertTrue(prim.vertices().contains("A"));
    }

    @Test
    public void primNormal() {
        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();
        IUndirectedGraph<String, WeightedEdge> prim = algo.prim(crearGrafoBase(), "A");
        double peso = 0;
        for (Edge<String, WeightedEdge> edge : prim.aristas()) {
            peso += edge.dato().getWeight();
        }
        assertTrue(prim.aristas().size() == prim.vertices().size() - 1); // Verifico que es un arbol
        assertEquals(peso, 7); // Es de costo minimo
    }

    @Test
    public void primArbolDesconectado() {
        UndirectedGraph<String, WeightedEdge> grafo = new UndirectedGraph<>();
        grafo.agregarVertices(List.of("A", "B", "C", "D"));

        grafo.agregarArista("A", "B", new WeightedEdge(10));
        grafo.agregarArista("C", "D", new WeightedEdge(1));

        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();
        IUndirectedGraph<String, WeightedEdge> prim = algo.prim(grafo, "A");

        assertEquals(prim.vertices().size(), 4);
        assertEquals(prim.aristas().size(), 2);
    }

    @Test
    public void searchMinEdgeVacio() {
        UndirectedGraph<String, WeightedEdge> grafoVacio = new UndirectedGraph<>();
        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();
        Edge<String, WeightedEdge> searchVacio = algo.searchMinEdge(grafoVacio, Set.of(), Set.of());
        assertTrue(searchVacio == null);
    }

    @Test
    public void searchMinEdgeNormal() {
        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();
        Edge<String, WeightedEdge> prim = algo.searchMinEdge(crearGrafoBase(), Set.of("A"), Set.of("B"));
        assertEquals(10, prim.dato().getWeight());
        assertTrue(prim.source().equals("A"));
        assertTrue(prim.target().equals("B"));
    }

    @Test
    public void searchMinEdgeNull() {
        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();
        Edge<String, WeightedEdge> prim = algo.searchMinEdge(crearGrafoBase(), Set.of("A"), Set.of("E"));
        assertEquals(null, prim);
    }

    @Test
    public void beaGrafoVacio() {
        UndirectedGraph<String, WeightedEdge> grafoVacio = new UndirectedGraph<>();
        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();

        List<String> visitados = new ArrayList<>();
        algo.bea(grafoVacio, visitados::add);

        assertTrue(visitados.isEmpty());
    }

    @Test
    public void beaVisitaTodosUnaVez() {
        UndirectedGraph<String, WeightedEdge> grafo = new UndirectedGraph<>();
        grafo.agregarVertice("A");
        grafo.agregarVertice("B");
        grafo.agregarVertice("C");
        grafo.agregarVertice("D");
        grafo.agregarArista("A", "B", new WeightedEdge(1));
        grafo.agregarArista("B", "C", new WeightedEdge(1));
        grafo.agregarArista("C", "D", new WeightedEdge(1));

        UndirectedGraphAlgorithm algo = new UndirectedGraphAlgorithm();
        List<String> visitados = new ArrayList<>();
        algo.bea(grafo, visitados::add);

        assertEquals(4, visitados.size()); // visitó todos
        assertEquals(4, new HashSet<>(visitados).size()); // sin repetidos
        assertTrue(visitados.containsAll(List.of("A", "B", "C", "D")));
    }

}
