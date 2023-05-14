package com.uvg.gt;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import com.uvg.gt.Model.Node;
import com.uvg.gt.Model.Relationship;

public interface IGraph {

    /**
     * Add a node the graph
     * 
     * @param node
     */
    public void addNode(Node node);

    /**
     * Remove a existing node from the grah.
     * 
     * @param node Node's label.
     * @return
     */
    public Node removeNode(String node);

    /**
     * Add a relation between 2 Nodes. It creates new Nodes
     * if necessary.
     * @param relation Relationship object.
     */
    public void addRelation(Relationship relation);

    /**
     * Removes a directed relation that BEGINS on start TO end.
     * Throws exception if nodes do not exist.
     *
     * @param start Start of the relationship
     * @param end End of relationship
     */
    public void removeRelation(Node start, Node end);

    /**
     * @return Returns the list of existing nodes in the graph.
     */
    public List<Node> getNodes();

    /**
     * Gets the relation between two nodes.
     * 
     * @param origin
     * @param destination
     * @return the relation between the nodes if there is one.
     */
    public Optional<Relationship> getRelation(Node origin, Node destination);

    /**
     * Returns a 2D matrix compound by 2 Maps. Where the combination of
     * 2 Node labels as keys must return the relation's weight for those to keys.
     * For non-existent relations will store Double.POSITIVE_INFINITY.
     * @return A matrix representation of the CURRENT graph.
     */
    public Map<String, Map<String, Double>> getGraphAsMatrix();

}