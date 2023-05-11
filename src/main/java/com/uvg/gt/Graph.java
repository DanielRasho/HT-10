package com.uvg.gt;

import java.util.List;
import java.util.Optional;

import com.uvg.gt.Model.Node;
import com.uvg.gt.Model.Relationship;

public class Graph implements IGraph {

    public Graph(List<Relationship> relations) {
    }

    @Override
    public void addNode(Node node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addNode'");
    }

    @Override
    public Node removeNode(String node) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeNode'");
    }

    @Override
    public List<Node> getNodes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNodes'");
    }

    @Override
    public void addRelation(Relationship relation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addRelation'");
    }

    @Override
    public void removeRelation(Node start, Node end) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeRelation'");
    }

    @Override
    public Optional<Relationship> getRelation(Node origin, Node destination) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRelation'");
    }

}
