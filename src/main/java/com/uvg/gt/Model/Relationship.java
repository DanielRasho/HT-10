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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((origin == null) ? 0 : origin.hashCode());
        result = prime * result + ((destination == null) ? 0 : destination.hashCode());
        result = prime * result + ((currentClimate == null) ? 0 : currentClimate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Relationship other = (Relationship) obj;
        if (origin == null) {
            if (other.origin != null)
                return false;
        } else if (!origin.equals(other.origin))
            return false;
        if (destination == null) {
            if (other.destination != null)
                return false;
        } else if (!destination.equals(other.destination))
            return false;
        if (currentClimate != other.currentClimate)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s -[%s]-> %s", origin, getWeight(), destination);
    }
}