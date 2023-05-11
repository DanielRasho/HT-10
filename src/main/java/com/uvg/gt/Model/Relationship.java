package com.uvg.gt.Model;

import java.util.HashMap;

public class Relationship {
    private Node origin;
    private Node destination;
    private HashMap<Climates, Integer> weights;
    private Climates currentClimate = Climates.NORMAL;

    public Relationship(Node origin, Node destination, HashMap<Climates, Integer> weights) {
        this.origin = origin;
        this.destination = destination;
        this.weights = weights;
    }

    public Node getOrigin() {
        return origin;
    }

    public Node getDestination() {
        return destination;
    }

    public int getWeight() {
        return weights.get(currentClimate);
    }

    public Climates getClimate() {
        return currentClimate;
    }

    public void changeClimate(Climates newClimate) {
        currentClimate = newClimate;
    }
}