package com.uvg.gt;

import java.util.HashMap;

public class Node {

    /** Used to identify easily within a graph.*/
    int id;

    String label;

    HashMap<Node, Integer> neighbors;

    public Node(String label){
        this.label = label;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addNeighbor (Node neighbor, int weight) {

    }
}
