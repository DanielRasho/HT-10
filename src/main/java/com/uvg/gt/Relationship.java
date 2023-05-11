package com.uvg.gt;

public class Relationship {
    private Node origin;
    private Node destination;
    private int weight;

    public Relationship(Node origin, Node destination, int weight) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
    }

    public Node getOrigin() {
        return origin;
    }

    public Node getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
