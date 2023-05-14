package com.uvg.gt;

import com.uvg.gt.Model.DataParser;
import com.uvg.gt.Model.Node;
import com.uvg.gt.Model.Relationship;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PathFinderTest {

    static Graph graph;
    static DataParser parser = new DataParser();
    static PathFinder pathFinder;

    @BeforeAll
    static void setUp() {
        List<Relationship> relations = new ArrayList<>();
        relations.add(parser.parse("BuenosAires SaoPaulo 10 15 20 50"));
        relations.add(parser.parse("BuenosAires Lima 2 20 30 70"));
        relations.add(parser.parse("Lima SaoPaulo 2 12 15 20"));
        graph = new Graph(relations);
        pathFinder = new PathFinder(graph);
    }

    @Test
    @Order(1)
    void matrixAsTable() {
        System.out.println(pathFinder.adjacencyMatrixAsTable());
        System.out.println(pathFinder.pathsAsTable());
    }

    @Test
    @Order(2)
    void updateShortestPath() {
        pathFinder.updateShortestPath();
        System.out.println(pathFinder.distanceAsTable());
        System.out.println(pathFinder.pathsAsTable());
    }

    @Test
    @Order(3)
    void constructShortestPath() {
        Node origin = new Node("BuenosAires");
        Node destination = new Node("SaoPaulo");
        System.out.println(pathFinder.constructPath(origin, destination).get());
    }

    @Test
    @Order(4)
    void getCenterNode() {
        System.out.println(pathFinder.getCentralNode());
    }
}