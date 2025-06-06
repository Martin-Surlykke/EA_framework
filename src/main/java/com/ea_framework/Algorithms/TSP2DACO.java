package com.ea_framework.Algorithms;

import com.ea_framework.ACOTypes.ACOType;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.TSP2DACOConfig;

public class TSP2DACO implements Algorithm {

    // Ant colony optimization for TSP (Traveling Salesman Problem)

    // This implementation uses a 2D array for pheromone and heuristic information

    // A certaint ACOType is used to define the behavior of pheromone initialization and update rules
    private ACOType type;

    // Pheromone and heuristic information matrices
    private double[][] tau;
    private double[][] eta;

    // Distance matrix representing the TSP graph
    private double[][] distanceMatrix;
    private int[] nodes;


    // Current solution and fitness tracking
    private int[] currentSolution;
    private double currentFitness = Double.MAX_VALUE;
    private double bestFitness = Double.MAX_VALUE;
    private int bestIteration = 0;


    // Parameters for pheromone and heuristic influence
    private double alpha;
    private double beta;

    @Override
    public void apply(AlgorithmConfig config) {
        if (!(config instanceof TSP2DACOConfig acoConfig)) {
            throw new IllegalArgumentException("Expected TSP2DACOConfig");
        }
        // Initialize the ACOType, distance matrix, initial tour, and parameters
        this.type = acoConfig.type();
        this.distanceMatrix = acoConfig.distanceMatrix();
        this.nodes = acoConfig.initialTour();

        this.alpha = acoConfig.getAlpha();
        this.beta = acoConfig.getBeta();

        this.type = acoConfig.type();
        this.type.setEvaporationRate(acoConfig.getRho());

        int n = nodes.length;
        this.tau = new double[n][n];
        this.eta = new double[n][n];

        // Initialize pheromones and heuristic information
        type.initializePheromones(tau, n);
        initEta();
    }

    // Initializes the heuristic information (eta) based on the distance matrix
    private void initEta() {
        int n = nodes.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                eta[i][j] = distanceMatrix[i][j] == 0 ? 0 : 1.0 / distanceMatrix[i][j];
            }
        }
    }


    // Runs the ACO algorithm as a given iteration
    @Override
    public void run(int iteration) {
        int[] tour = constructTour();
        double fitness = evaluateFitness(tour);

        // Create a boolean matrix to track used edges in the tour
        boolean[][] edgeUsed = new boolean[nodes.length][nodes.length];
        for (int i = 0; i < tour.length - 1; i++) {
            edgeUsed[tour[i]][tour[i + 1]] = true;
            edgeUsed[tour[i + 1]][tour[i]] = true;
        }
        edgeUsed[tour[tour.length - 1]][tour[0]] = true;
        edgeUsed[tour[0]][tour[tour.length - 1]] = true;

        // Update pheromones using the ACOType's update method
        type.updatePheromones(tau, tour, fitness, edgeUsed);


        // Update current solution and fitness
        currentSolution = tour;
        currentFitness = fitness;

        // Update best fitness and iteration if current is better
        if (fitness < bestFitness) {
            bestFitness = fitness;
            bestIteration = iteration;
        }
    }


    // Evaluates the fitness of a given tour by summing the distances between consecutive nodes
    private double evaluateFitness(int[] tour) {
        double total = 0;
        for (int i = 1; i < tour.length; i++) {
            total += distanceMatrix[tour[i - 1]][tour[i]];
        }
        total += distanceMatrix[tour[tour.length - 1]][tour[0]];
        return total;
    }


    // Constructs a tour using a probabilistic approach based on pheromone and heuristic information
    private int[] constructTour() {
        int[] tour = new int[nodes.length];
        boolean[] visited = new boolean[nodes.length];

        // Start from a random node
        int currentNode = nodes[new java.util.Random().nextInt(nodes.length)];
        tour[0] = currentNode;
        visited[currentNode] = true;

        // Construct the tour by selecting the next node based on pheromone and heuristic information
        for (int step = 1; step < nodes.length; step++) {
            int nextNode = selectNextNode(currentNode, visited);
            tour[step] = nextNode;
            visited[nextNode] = true;
            currentNode = nextNode;
        }

        return tour;
    }


    // Selects the next node to visit based on pheromone and heuristic information
    private int selectNextNode(int currentNode, boolean[] visited) {
        double[] probabilities = new double[nodes.length];
        double sum = 0.0;

        // Calculate probabilities for each unvisited node
        for (int j = 0; j < nodes.length; j++) {
            if (visited[j] || j == currentNode) continue;
            double tauVal = Math.pow(tau[currentNode][j], alpha);
            double etaVal = Math.pow(eta[currentNode][j], beta);
            probabilities[j] = tauVal * etaVal;
            sum += probabilities[j];
        }


        // If no unvisited nodes are left, return the first unvisited node
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
