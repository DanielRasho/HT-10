package com.uvg.gt.Model;

import java.util.HashMap;

public class Node {

    /** Used to identify easily within a graph. */
    int id;

    String label;

    HashMap<Node, Integer> neighbors;

    public Node(String label) {
        this.label = label;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void addNeighbor(Node neighbor, int weight) {
        neighbors.put(neighbor, weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }

        Node other = (Node) obj;
        return id == other.getId();
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return label;
    }
}
