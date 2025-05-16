package com.ea_framework.Algorithms;

import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.TSP2DACOConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AntColonyOptimization implements Algorithm {

    private double[][] tau;
    private double[][] eta;
    private double[][] distanceMatrix;
    private int[] nodes;

    private double rho = 0.1;
    private double tauMax;
    private double tauMin;

    private int[] currentTour;
    private double currentFitness;

    private int[] bestTour;
    private double bestFitness = Double.MAX_VALUE;
    private int bestIteration = 0;

    private final double alpha = 1.5;
    private final double beta = 2.5;

    @Override
    public void apply(AlgorithmConfig config) {
        if (config instanceof TSP2DACOConfig acoConfig) {

        } else {
            throw new IllegalArgumentException("Invalid configuration type for AntColonyOptimization");
        }
    }

    @Override
    public void run(int iterations) {

        int n = nodes.length;
        tau = new double[n][n];
        eta = new double[n][n];

        tauMax = 1 - (1.0 / n);
        tauMin = 1 / Math.pow(n, 2);

        initTau(n);
        initEta(distanceMatrix, n);

        int[] tour = construct(nodes, tau, eta, alpha, beta);
        update(tour);

        bestTour = tour;
        bestFitness = computeTourLength(tour);
        currentTour = tour;
        currentFitness = bestFitness;

        for (int x = 1; x < iterations; x++) {
            int[] candidate = construct(nodes, tau, eta, alpha, beta);
            update(candidate);
            double candidateFitness = computeTourLength(candidate);
            currentTour = candidate;
            currentFitness = candidateFitness;

            if (candidateFitness < bestFitness) {
                bestFitness = candidateFitness;
                bestTour = candidate;
                bestIteration = x;
            }
        }
    }

    private void initTau(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tau[i][j] = 1.0 / n;
            }
        }
    }

    private void initEta(double[][] distanceMatrix, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                eta[i][j] = distanceMatrix[i][j] == 0.0 ? 0.0 : 1.0 / distanceMatrix[i][j];
            }
        }
    }

    public static int[] construct(int[] nodes, double[][] pheromones, double[][] heuristic, double alpha, double beta) {
        int[] construct = new int[nodes.length];
        Random rand = new Random();
        int x = rand.nextInt(nodes.length);
        int currentNode = nodes[x];
        construct[0] = currentNode;

        List<Integer> unvisited = new ArrayList<>();
        for (int node : nodes) unvisited.add(node);
        unvisited.remove(Integer.valueOf(currentNode));

        for (int i = 1; i < nodes.length; i++) {
            double R = calculateR(currentNode, pheromones, heuristic, alpha, beta, unvisited);
            currentNode = getNextNode(currentNode, R, pheromones, heuristic, unvisited, alpha, beta);
            unvisited.remove(Integer.valueOf(currentNode));
            construct[i] = currentNode;
        }

        return construct;
    }

    public static double calculateR(int currentNode, double[][] pheromones, double[][] heuristic, double alpha, double beta, List<Integer> unvisited) {
        double R = 0.0;
        for (int node : unvisited) {
            double a = Math.pow(pheromones[currentNode][node], alpha);
            double b = Math.pow(heuristic[currentNode][node], beta);
            R += a * b;
        }
        return R;
    }

    public static int getNextNode(int currentNode, double R, double[][] pheromones, double[][] heuristic, List<Integer> unvisited, double alpha, double beta) {
        double[] cumulativeProbabilities = new double[unvisited.size()];
        double total = 0.0;

        for (int i = 0; i < unvisited.size(); i++) {
            int node = unvisited.get(i);
            double a = Math.pow(pheromones[currentNode][node], alpha);
            double b = Math.pow(heuristic[currentNode][node], beta);
            total += (a * b) / R;
            cumulativeProbabilities[i] = total;
        }

        double rand = Math.random();
        for (int i = 0; i < cumulativeProbabilities.length; i++) {
            if (rand <= cumulativeProbabilities[i]) {
                return unvisited.get(i);
            }
        }
        return unvisited.get(unvisited.size() - 1);
    }

    private void update(int[] tour) {
        int n = tour.length;

        for (int i = 0; i < n - 1; i++) {
            int from = tour[i];
            int to = tour[i + 1];
            tau[from][to] = Math.min((1 - rho) * tau[from][to] + rho, tauMax);
            tau[to][from] = tau[from][to];
        }

        int last = tour[n - 1];
        int first = tour[0];
        tau[last][first] = Math.min((1 - rho) * tau[last][first] + rho, tauMax);
        tau[first][last] = tau[last][first];

        for (int i = 0; i < tau.length; i++) {
            for (int j = 0; j < tau.length; j++) {
                if (!inTour(i, j, tour)) {
                    tau[i][j] = Math.max((1 - rho) * tau[i][j], tauMin);
                }
            }
        }
    }

    private boolean inTour(int i, int j, int[] tour) {
        for (int k = 0; k < tour.length - 1; k++) {
            if ((tour[k] == i && tour[k + 1] == j) || (tour[k] == j && tour[k + 1] == i)) {
                return true;
            }
        }
        return (tour[tour.length - 1] == i && tour[0] == j) || (tour[tour.length - 1] == j && tour[0] == i);
    }

    private double computeTourLength(int[] tour) {
        double length = 0.0;
        for (int i = 1; i < tour.length; i++) {
            length += distanceMatrix[tour[i - 1]][tour[i]];
        }
        length += distanceMatrix[tour[tour.length - 1]][tour[0]];
        return length;
    }

    public int[][] getBestTourEdges() {
        return transformEdgeList(bestTour);
    }

    private int[][] transformEdgeList(int[] tour) {
        int[][] edgeList = new int[tour.length][2];
        for (int i = 0; i < tour.length - 1; i++) {
            edgeList[i][0] = tour[i] + 1;
            edgeList[i][1] = tour[i + 1] + 1;
        }
        edgeList[tour.length - 1][0] = tour[tour.length - 1] + 1;
        edgeList[tour.length - 1][1] = tour[0] + 1;
        return edgeList;
    }

    @Override
    public Object getCurrentSolution() {
        return currentTour != null ? Arrays.copyOf(currentTour, currentTour.length) : null;
    }

    @Override
    public void setCurrentSolution(Object solution) {
        if (solution instanceof int[]) {
            currentTour = Arrays.copyOf((int[]) solution, ((int[]) solution).length);
            currentFitness = computeTourLength(currentTour);
        }
    }

    @Override
    public Double getCurrentFitness() {
        return currentFitness;
    }

    @Override
    public Double getBestFitness() {
        return bestFitness;
    }

    @Override
    public int getBestIteration() {
        return bestIteration;
    }
}
