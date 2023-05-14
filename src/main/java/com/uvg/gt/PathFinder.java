package com.uvg.gt;

import java.util.*;

import com.uvg.gt.Model.Node;

public class PathFinder implements IPathFinder {
    private final IGraph graph;
    private Map<String, Map<String, Double>> adjacencyMatrix;
    private Map<String, Map<String, Double>> distances = new TreeMap<>();
    private Map<String, Map<String, String>> paths = new TreeMap<>();

    public PathFinder(IGraph graph) {
        this.graph = graph;
        this.adjacencyMatrix = graph.getGraphAsMatrix();
        setUpMatrices();
    }

    @Override
    public void updateShortestPath() {
        updateAdjacencyMatrix();
        setUpMatrices();
        for (String node : distances.keySet()) {
            for (String origin : distances.keySet()) {
                for (String destination : distances.keySet()) {
                    Double originalDistance = distances.get(origin).get(destination);
                    Double intermediateDistance = distances.get(origin).get(node)
                            + distances.get(node).get(destination);
                    if (intermediateDistance < originalDistance) {
                        distances.get(origin).put(destination, intermediateDistance);
                        paths.get(origin).put(destination, node);
                    }
                }
            }
        }
    }

    @Override
    public Optional<List<Node>> constructPath(Node origin, Node destination) {
        List<Node> path = new ArrayList<>();
        String at = origin.getLabel();
        String end = destination.getLabel();

        for (; !at.equals(end); at = paths.get(at).get(end)) {
            if (at.equals("None"))
                return Optional.empty();
            path.add(new Node(at));
        }

        if (paths.get(at).get(end).equals("None"))
            return Optional.empty();
        path.add(new Node(end));

        return Optional.of(path);
    }

    @Override
    public Double getShortestPath(Node origin, Node destination) {
        return distances.get(origin.getLabel()).get(destination.getLabel());
    }

    @Override
    public Node getCentralNode() {
        TreeMap<String, Double> nodesEccentricity = new TreeMap<>();

        // Getting the eccentricity for each node.
        for (String column : adjacencyMatrix.keySet()) {
            List<Double> distancesValues = new ArrayList<>();
            for (String row : adjacencyMatrix.keySet()) {
                distancesValues.add(distances.get(row).get(column));
            }
            nodesEccentricity.put(column, distancesValues.stream()
                    .mapToDouble(v -> v).max().getAsDouble());
        }

        // Finding the node with minimum eccentricity.
        Optional<String> center = nodesEccentricity.keySet().stream()
                .min(Comparator.comparingDouble(nodesEccentricity::get));

        return new Node(center.get());
    }

    public void updateAdjacencyMatrix() {
        this.adjacencyMatrix = graph.getGraphAsMatrix();
    }

    public void setUpMatrices() {
        for (String node : adjacencyMatrix.keySet()) {
            distances.put(node, new TreeMap<String, Double>());
            paths.put(node, new TreeMap<String, String>());
        }

        for (String row : adjacencyMatrix.keySet()) {
            for (String column : adjacencyMatrix.keySet()) {
                Double distance = adjacencyMatrix.get(row).get(column);
                distances.get(row).put(column, distance);
                if (distance.equals(Double.POSITIVE_INFINITY))
                    paths.get(row).put(column, "None");
                else
                    paths.get(row).put(column, column);
            }
        }
    }

    public String adjacencyMatrixAsTable() {
        return matrixAsTable(adjacencyMatrix);
    }

    public String distanceAsTable() {
        return matrixAsTable(distances);
    }

    public String pathsAsTable() {
        return matrixAsTable(paths);
    }

    private <T, V, J> String matrixAsTable(Map<T, Map<V, J>> matrix) {
        StringBuilder message = new StringBuilder();

        // CREATE HEADERS
        message.append(String.format("%15s| ", ""));
        for (T column : matrix.keySet()) {
            message.append(String.format("%15s| ", column.toString()));
        }
        message.append("\n");

        // CREATE ROWS
        for (T row : matrix.keySet()) {
            message.append(String.format("%15s| ", row.toString()));
            for (V column : matrix.get(row).keySet()) {
                message.append(
                        String.format("%15s| ", matrix.get(row).get(column).toString()));
            }
            message.append("\n");
        }

        return message.toString();
    }

}
