package com.uvg.gt;

public class ParsedNode {
    private String from;
    private String to;
    private int rainTime;
    private int normalTime;
    private int stormTime;
    private int blizzardTime;

    public ParsedNode(String from, String to, int rainTime, int normalTime, int stormTime, int blizzardTime) {
        this.from = from;
        this.to = to;
        this.rainTime = rainTime;
        this.normalTime = normalTime;
        this.stormTime = stormTime;
        this.blizzardTime = blizzardTime;
    }

    public int getRainTime() {
        return rainTime;
    }

    public int getNormalTime() {
        return normalTime;
    }

    public int getStormTime() {
        return stormTime;
    }

    public int getBlizzardTime() {
        return blizzardTime;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}