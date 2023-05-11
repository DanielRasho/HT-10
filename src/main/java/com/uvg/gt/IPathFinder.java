package com.uvg.gt;

import java.util.List;

public interface IPathFinder {

    /**
     * After modifying the graph this function will
     * carry over Floyd's Algorithm to recalculate the shortest path.
     * The Shortest path will not be update, unless this function
     * is explicitly called.
     */
    public void updateShortestPath();

    /**
     * Return the ordered list of nodes, to move from one to another.
     * @param StartNode Label of the starting node.
     * @param EndNode Label of the ending node.
     * @return List of steps to move from one to another.
     */
    public List<Node> constructPath(String StartNode, String EndNode);
}
