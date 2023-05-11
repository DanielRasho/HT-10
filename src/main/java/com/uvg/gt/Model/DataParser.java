package com.uvg.gt.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {
    private List<Climates> climates = new ArrayList<>() {
        {
            add(Climates.NORMAL);
            add(Climates.RAIN);
            add(Climates.BLIZZARD);
            add(Climates.STORM);
        }
    };

    public Relationship parse(String line) {
        var parts = line.split(" ", 6);
        List<Integer> times = new ArrayList<>(4);
        for (int i = 2; i < parts.length; i++) {
            times.add(Integer.parseInt(parts[i]));
        }

        HashMap<Climates, Integer> weights = new HashMap<>(4);
        for (int i = 0; i < times.size(); i++) {
            weights.put(climates.get(i), times.get(i));
        }

        return new Relationship(new Node(parts[0]), new Node(parts[1]), weights);
    }

}