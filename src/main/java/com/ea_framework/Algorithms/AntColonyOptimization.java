package com.ea_framework.Algorithms;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AntColonyOptimization {

    static int n; // number of nodes
    int m; // number of ants

    int iterations; // number of iterations

    static double [][] pheromoneMatrix = new double[n][n];

    static double rho = 0.1; // evaporation rate

    static double [][] h = new double[n][n]; // heuristic information matrix

    static double Lnn;

    public static int [][]  ACO(double [][] distanceMatrix, int m, int iterations) {

        Random rand = new Random();

        n = distanceMatrix.length;

        Lnn = getLnn(distanceMatrix, rand.nextInt(n));

        h = initHeuristicMatrix(distanceMatrix);

        pheromoneMatrix = initPheromoneMatrix(n, Lnn);

        ArrayList<Integer> bestTour = runAnts(distanceMatrix, m, iterations);

        int [][] edges = transFormEdgeList(bestTour);

        return edges;
    }

    private static int[][] transFormEdgeList(ArrayList<Integer> bestTour) {

        int [][] edges = new int[bestTour.size()][2];

        for (int i = 0; i < bestTour.size(); i++) {
            int node1 = bestTour.get(i);
            int node2;
            if (i == bestTour.size()-1) {
                node2 = bestTour.get(0);
            } else {
                node2 = bestTour.get(i+1);
            }

            edges[i][0] = node1+1;
            edges[i][1] = node2+1;

        }

        return edges;
    }


    public static ArrayList<Integer> runAnts(double [][] distanceMatrix, int m, int iterations) {

        Random rand = new Random();

        double minimumLength = Double.MAX_VALUE;
        ArrayList<Integer> bestTour = null;

        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < m; j++) {

                int startNode = rand.nextInt(n);

                ArrayList<Integer> visitedNotes = new ArrayList<>();
                visitedNotes.add(startNode);

                int currentNode = startNode;

                double currentLength = 0;

                for (int k = 1; k < n; k++) {
                    int nextNode = getNextNode(currentNode, visitedNotes);

                    currentLength += distanceMatrix[currentNode][nextNode];

                    visitedNotes.add(nextNode);
                    currentNode = nextNode;

                }

                currentLength += distanceMatrix[currentNode][startNode];

                if (currentLength < minimumLength) {
                    minimumLength = currentLength;
                    bestTour = new ArrayList<>(visitedNotes);
                }

            }

            updatePheromoneMatrix(bestTour, minimumLength);



        }
        return bestTour;

    }

    private static int getNextNode(int currentNode, ArrayList<Integer> visitedNotes) {

        int bestNode = -1;

        double bestValue = Double.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            if (!visitedNotes.contains(i)) {
                double value = Math.pow(pheromoneMatrix[currentNode][i], 1) * Math.pow(h[currentNode][i], 1);
                if (value > bestValue) {
                    bestValue = value;
                    bestNode = i;
                }
            }
        }

        if (bestNode == -1) {
            System.out.println("Error");
        }

        return bestNode;
    }


    private static double[][] initPheromoneMatrix(int n, double lnn) {
        double [][] p = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                p[i][j] = 1/(n*lnn);
            }
        }
        return p;
    }

    public static void updatePheromoneMatrix(ArrayList<Integer> tour, double tourLength) {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                pheromoneMatrix[i][j] *= (1 - rho);
            }
        }

    }

    private static double[][] initHeuristicMatrix(double[][] distanceMatrix) {
        double [][] heuristic_matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                heuristic_matrix[i][j] = 1 / distanceMatrix[i][j];
            }
        }

        return heuristic_matrix;
    }


    public static double getLnn(double [][] distanceMatrix, int startNode) {

        ArrayList<Integer> unvisitedNodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            unvisitedNodes.add(i);
        }
        unvisitedNodes.remove(startNode);


        int currentNode = startNode;

        double currentDistance = 0;

        while (!unvisitedNodes.isEmpty()) {

            int closestNode = -1;
            double closestDistance = Double.MAX_VALUE;

            for (int node : unvisitedNodes) {
                if (distanceMatrix[currentNode][node] < closestDistance) {
                    closestNode = node;
                    closestDistance = distanceMatrix[currentNode][node];
                }
            }

            currentDistance += closestDistance;

            currentNode = closestNode;

            unvisitedNodes.remove((Integer) closestNode);

        }

        currentDistance += distanceMatrix[currentNode][startNode];

        return currentDistance;


    }

}
