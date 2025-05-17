package com.ea_framework.Algorithms;

import com.ea_framework.ACOTypes.ACOType;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.TSP2DACOConfig;

public class TSP2DACO implements Algorithm {

    private ACOType type;

    private double[][] tau;
    private double[][] eta;

    private double[][] distanceMatrix;
    private int[] nodes;

    private int[] currentSolution;
    private double currentFitness = Double.MAX_VALUE;
    private double bestFitness = Double.MAX_VALUE;
    private int bestIteration = 0;

    private double alpha;
    private double beta;

    @Override
    public void apply(AlgorithmConfig config) {
        if (!(config instanceof TSP2DACOConfig acoConfig)) {
            throw new IllegalArgumentException("Expected TSP2DACOConfig");
        }
        this.type = acoConfig.type();
        this.distanceMatrix = acoConfig.distanceMatrix();
        this.nodes = acoConfig.initialTour();

        this.alpha = acoConfig.getAlpha();
        this.beta = acoConfig.getBeta();

        int n = nodes.length;
        this.tau = new double[n][n];
        this.eta = new double[n][n];

        type.initializePheromones(tau, n);
        initEta();
    }

    private void initEta() {
        int n = nodes.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                eta[i][j] = distanceMatrix[i][j] == 0 ? 0 : 1.0 / distanceMatrix[i][j];
            }
        }
    }

    @Override
    public void run(int iteration) {
        int[] tour = constructTour();
        double fitness = evaluateFitness(tour);

        boolean[][] edgeUsed = new boolean[nodes.length][nodes.length];
        for (int i = 0; i < tour.length - 1; i++) {
            edgeUsed[tour[i]][tour[i + 1]] = true;
            edgeUsed[tour[i + 1]][tour[i]] = true;
        }
        edgeUsed[tour[tour.length - 1]][tour[0]] = true;
        edgeUsed[tour[0]][tour[tour.length - 1]] = true;

        type.updatePheromones(tau, tour, fitness, edgeUsed);

        currentSolution = tour;
        currentFitness = fitness;

        if (fitness < bestFitness) {
            bestFitness = fitness;
            bestIteration = iteration;
        }
    }

    private double evaluateFitness(int[] tour) {
        double total = 0;
        for (int i = 1; i < tour.length; i++) {
            total += distanceMatrix[tour[i - 1]][tour[i]];
        }
        total += distanceMatrix[tour[tour.length - 1]][tour[0]];
        return total;
    }

    private int[] constructTour() {
        int[] tour = new int[nodes.length];
        boolean[] visited = new boolean[nodes.length];

        int currentNode = nodes[new java.util.Random().nextInt(nodes.length)];
        tour[0] = currentNode;
        visited[currentNode] = true;

        for (int step = 1; step < nodes.length; step++) {
            int nextNode = selectNextNode(currentNode, visited);
            tour[step] = nextNode;
            visited[nextNode] = true;
            currentNode = nextNode;
        }

        return tour;
    }

    private int selectNextNode(int currentNode, boolean[] visited) {
        double[] probabilities = new double[nodes.length];
        double sum = 0.0;

        for (int j = 0; j < nodes.length; j++) {
            if (visited[j] || j == currentNode) continue;
            double tauVal = Math.pow(tau[currentNode][j], alpha);
            double etaVal = Math.pow(eta[currentNode][j], beta);
            probabilities[j] = tauVal * etaVal;
            sum += probabilities[j];
        }

        double r = Math.random() * sum;
        for (int j = 0; j < nodes.length; j++) {
            if (visited[j] || j == currentNode) continue;
            r -= probabilities[j];
            if (r <= 0) return j;
        }

        for (int j = 0; j < nodes.length; j++) {
            if (!visited[j]) return j;
        }

        throw new IllegalStateException("No unvisited node left");
    }

    @Override public Object getCurrentSolution() { return currentSolution; }
    @Override public void setCurrentSolution(Object solution) { this.currentSolution = (int[]) solution; }
    @Override public Double getCurrentFitness() { return currentFitness; }
    @Override public Double getBestFitness() { return bestFitness; }
    @Override public int getBestIteration() { return bestIteration; }
}
