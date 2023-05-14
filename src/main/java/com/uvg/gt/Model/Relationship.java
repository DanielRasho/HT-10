package com.uvg.gt.Model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
public class Relationship {
    private Node origin;
    private Node destination;
    @Getter(AccessLevel.NONE)
    private HashMap<Climates, Integer> weights;
    @Setter
    private Climates currentClimate = Climates.NORMAL;

    public Relationship(Node origin, Node destination, HashMap<Climates, Integer> weights) {
        this.origin = origin;
        this.destination = destination;
        this.weights = weights;
    }

    public int getWeight() {
        return weights.get(currentClimate);
    }

    public void setClimate(Climates newClimate) {
        currentClimate = newClimate;
    }

    public Climates getCurrentClimate() {
        return currentClimate;
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