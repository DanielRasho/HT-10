package com.uvg.gt;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.uvg.gt.Model.Node;
import com.uvg.gt.Model.Relationship;

public class Graph implements IGraph {

    List<Node> nodes;
    List<Relationship> relations;

    public Graph(List<Relationship> relations){
        this.relations = relations;
        this.nodes = getNodes();
    }

    @Override
    public void addNode(Node node) {
        if (!nodeExist(node))
            nodes.add(node);
    }

    @Override
    public Node removeNode(String nodeLabel) {
        Node node = new Node(nodeLabel);
        if (!nodeExist(nodeLabel))
            throw new RuntimeException("Error: Cannot remove a node that does not exist within the graph.");
        List<Relationship> nodeRelations = relations.stream()
                        .filter(o -> o.getOrigin().equals(node)
                        || o.getDestination().equals(node)).toList();
        if (nodeRelations.size() != 0)
            throw new RuntimeException(String.format("Error: Cannot remove %s node first remove its relations", node));
        nodes.remove(node);
        return node;
    }

    @Override
    public void addRelation(Relationship relation) {
        if(existRelation(relation))
            return;
        addNode(relation.getOrigin());
        addNode(relation.getDestination());
        relations.add(relation);
    }

    @Override
    public void removeRelation(Node origin, Node destination) {
        Optional<Relationship> relation = getRelation(origin, destination);
        relation.ifPresent(relationship -> relations.remove(relationship));
    }

    @Override
    public List<Node> getNodes() {
        List<Node> nodes;
        Stream<Node> origins = this.relations.stream()
                        .map(Relationship::getOrigin);
        Stream<Node> destinations = this.relations.stream()
                .map(Relationship::getDestination);
        nodes = Stream.concat(origins, destinations)
                .distinct()
                .collect(Collectors.toList());
        return nodes;
    }

    @Override
    public Optional<Relationship> getRelation(Node origin, Node destination) {
        return relations.stream().filter(o -> o.getOrigin().equals(origin))
                                        .filter(d -> d.getDestination().equals(destination))
                                        .findFirst();
    }

    @Override
    public Map<String, Map<String, Double>> getGraphAsMatrix(){

        TreeMap<String, Map<String, Double>> matrix = new TreeMap<>();

        // Selecting a node as origin
        for(Node origin : nodes){
            TreeMap<String, Double> subMatrix = new TreeMap<>();
            // Setting relation weight between a node an the rest.
            for (Node destination : nodes){
                Double weight;
                // The weight of node to itself must be 0;
                if (origin.equals(destination))
                    weight = 0.0;
                // Else the weight can be POSITIVE INFINITY (There's no relation) or a DOUBLE (There's relation)
                else {
                    Optional<Relationship> relation = getRelation(origin, destination);
                    weight = relation.map(relationship -> (double) relationship.getWeight())
                            .orElse(Double.POSITIVE_INFINITY);
                }

                // Adding relation
                subMatrix.put(destination.getLabel(), weight);
            }
            // Add all relations
            matrix.put(origin.getLabel(), subMatrix);
        }

        return matrix;
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append("Nodes: \n");
        for (Node node: nodes) {
            message.append(node.toString() + "\n");
        }
        message.append("Relations: \n");
        for (Relationship relation: relations) {
            message.append(relation.toString() + "\n");
        }
        return message.toString();
    }

    public boolean nodeExist(Node node){
        return nodes.contains(node);
    }

    public boolean nodeExist(String nodeLabel){
        return nodes.contains(new Node(nodeLabel));
    }

    public boolean existRelation(Relationship relation){
        return relations.contains(relation);
    }
}
