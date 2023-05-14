package com.uvg.gt;

import com.uvg.gt.Model.DataParser;
import com.uvg.gt.Model.Node;
import com.uvg.gt.Model.Relationship;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GraphTest {

    static Graph graph;
    static DataParser parser = new DataParser();

    @BeforeAll
    static void setUp() {
        List<Relationship> relations = new ArrayList<>();
        relations.add(parser.parse("BuenosAires SaoPaulo 10 15 20 50"));
        relations.add(parser.parse("BuenosAires Lima 15 20 30 70"));
        relations.add(parser.parse("Lima Quito 10 12 15 20"));
        graph = new Graph(relations);
    }

    @Test
    @Order(1)
    void addNode() {
        Node node = new Node("NewYork");
        graph.addNode(node);
        System.out.println(graph.toString());
        assertTrue(graph.nodeExist(node));
    }

    @Test
    @Order(2)
    void addRelation() {
        Relationship relationship = parser.parse("NewYork SaoPaulo 10 15 20 50");
        graph.addRelation(relationship);
        System.out.println(graph.toString());
    }

    @Test
    @Order(3)
    void removeRelation() {
        Node origin = new Node("NewYork");
        Node destination = new Node("SaoPaulo");
        graph.removeRelation(origin, destination);
        System.out.println(graph.toString());
    }

    @Test
    @Order(4)
    void removeNode() {
        Node node = new Node("NewYork");
        graph.removeNode(node.getLabel());
        System.out.println(graph.toString());
        assertFalse(graph.nodeExist(node));
    }

    @Test
    @Order(5)
    void getNodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node("BuenosAires"));
        nodes.add(new Node("Lima"));
        nodes.add(new Node("SaoPaulo"));
        nodes.add(new Node("Quito"));
        assertEquals(nodes, graph.getNodes());
    }

    @Test
    @Order(6)
    void getRelation() {
        Node origin = new Node("Lima");
        Node destination = new Node("Quito");
        int relationWeight = graph.getRelation(origin, destination).get().getWeight();
        assertEquals(10, relationWeight);
    }

    @Test
    @Order(7)
    void getUndefinedRelation() {
        Node origin = new Node("Lima");
        Node destination = new Node("Harry Potter");
        boolean existRelation = graph.getRelation(origin, destination).isEmpty();
        assertTrue(existRelation);
    }

    @Test
    @Order(8)
    void getGraphAsMatrix() {
        System.out.println(graph.getGraphAsMatrix().toString());
    }
}