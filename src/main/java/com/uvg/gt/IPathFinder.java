package com.uvg.gt;

import java.util.List;
import java.util.Optional;

import com.uvg.gt.Model.Node;

public interface IPathFinder {

    /**
     * After modifying the graph this function will
     * carry over Floyd's Algorithm to recalculate the shortest path.
     * The Shortest path will not be updated, unless this function
     * is explicitly called.
     */
    public void updateShortestPath();

    /**
     * Get's the node at the center of the graph. Meaning, the node which is the
     * closest to all cities.
     */
    public Node getCentralNode();

    /**
     * Return the ordered list of nodes, to move from one to another.
     * 
     * @param origin      Starting node.
     * @param destination Ending node.
     * @return List of steps to move from one to another.
     */
    public Optional<List<Node>> constructPath(Node origin, Node destination);

    /**
     * Returns the shortest distance needed to move from one node to another.
     * 
     * @param origin      Starting node
     * @param destination Ending node.
     * @return
     */
    public Double getShortestPath(Node origin, Node destination);
}
