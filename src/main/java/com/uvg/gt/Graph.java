package com.uvg.gt;

import java.util.List;

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
    public void addRelation(String start, String end, int weight) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addRelation'");
    }

    @Override
    public void removeRelation(String start, String end) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeRelation'");
    }

    @Override
    public List<Node> getNodes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNodes'");
    }

}
