package com.uvg.gt;

import java.util.List;
import java.util.Optional;

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
     * Creates a directed relation FROM start TO end.
     * 
     * If the relation already exists it just updates it.
     * 
     * @param start  Label of the starting node.
     * @param end    Label of the ending node.
     * @param weight Relation's weight.
     */
    public void addRelation(Relationship relation);

    /**
     * Removes a directed relation that BEGINS on start TO end.
     * 
     * @param start
     * @param end
     */
    public void removeRelation(Node start, Node end);

    /**
     * @return Returns the list of existing nodes in the graph.
     */
    public List<Node> getNodes();

    /**
     * Get's the relation between this two nodes.
     * 
     * @param origin
     * @param destination
     * @return the relation between the nodes if there is one.
     */
    public Optional<Relationship> getRelation(Node origin, Node destination);
}
