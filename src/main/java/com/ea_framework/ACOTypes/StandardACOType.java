package com.ea_framework.ACOTypes;

public class StandardACOType implements ACOType {

    // Parameters for pheromone update
    private double rho = 0.1;
    private final double Q = 100.0;


    // Initialization of pheromones
    @Override
    public void initializePheromones(double[][] tau, int length) {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                // tau is set to 1/n
                tau[i][j] = 1.0 / length;
            }
        }
    }

    // Selection of next node based on pheromone and heuristic information
    @Override
    public void updatePheromones(double[][] tau, int[] tour, double fitness, boolean[][] edgesUsed) {
        int n = tour.length;

        // Evaporate pheromones
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tau[i][j] *= (1 - rho);
            }
        }
        // Update pheromones based on the tour

        for (int i = 0; i < n - 1; i++) {
            int from = tour[i], to = tour[i + 1];
            tau[from][to] += Q / fitness;
            tau[to][from] = tau[from][to];
        }
        // Add pheromone for the edge connecting the last and first nodes
        int last = tour[n - 1], first = tour[0];
        tau[last][first] += Q / fitness;
        tau[first][last] = tau[last][first];
    }

    @Override
    public void setEvaporationRate(double rho) {
        this.rho = rho;
    }
}
