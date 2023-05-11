package com.uvg.gt;

import java.util.List;

import com.uvg.gt.Model.Node;
import com.uvg.gt.Model.Relationship;

public interface IPathFinder {

    /**
     * After modifying the graph this function will
     * carry over Floyd's Algorithm to recalculate the shortest path.
     * The Shortest path will not be update, unless this function
     * is explicitly called.
     */
    public void updateShortestPath();

    /**
     * Return the ordered list of relations, to move from one to another.
     * 
     * @param StartNode Starting node.
     * @param EndNode   Ending node.
     * @return List of steps to move from one to another.
     */
    public List<Relationship> constructPath(Node StartNode, Node EndNode);
}
