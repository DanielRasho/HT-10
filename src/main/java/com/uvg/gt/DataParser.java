package com.uvg.gt;

public class DataParser {

    public ParsedNode parse(String line) {
        var parts = line.split(" ", 6);
        var times = new int[4];
        for (int i = 2; i < parts.length; i++) {
            times[i - 2] = Integer.parseInt(parts[i]);
        }
        return new ParsedNode(parts[0], parts[1], times[0], times[1], times[2], times[3]);
    }

}