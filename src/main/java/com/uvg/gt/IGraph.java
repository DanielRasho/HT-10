package com.uvg.gt;

import java.util.List;

public interface IGraph {

    /**
     * Add a node the graph
     * @param node
     */
    public void addNode (Node node);

    /**
     * Remove a existing node from the grah.
     * @param node Node's label.
     * @return
     */
    public Node removeNode (String node);

    /**
     * Creates a directed relation FROM start TO end.
     * @param start Label of the starting node.
     * @param end Label of the ending node.
     * @param weight Relation's weight.
     */
    public void addRelation (String start, String end, int weight);

    /**
     * Removes a directed relation that BEGINS on start TO end.
     * @param start
     * @param end
     */
    public void removeRelation (String start, String end);

    /**
     * @return  Returns the list of existing nodes in the graph.
     */
    public List<Node> getNodes ();
}
