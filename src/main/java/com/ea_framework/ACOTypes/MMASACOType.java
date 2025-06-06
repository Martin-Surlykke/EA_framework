package com.ea_framework.ACOTypes;

public class MMASACOType implements ACOType {

    // MMAS stands for Max-Min Ant System, a variant of Ant Colony Optimization
    private double rho = 0.1; // rho is the pheromone evaporation rate, typically set to a small value
    private double tauMax;
    // tauMin is the minimum pheromone level, which is set to a small value
    private double tauMin;
    // tau is the pheromone matrix, which is a 2D array representing pheromone levels between nodes


    // Initializes pheromones for the MMAS algorithm
    @Override
    public void initializePheromones(double[][] tau, int length) {
        tauMax = 1 - (1.0 / length);
        tauMin = 1 / Math.pow(length, 2);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                tau[i][j] = 1.0 / length;
            }
        }
    }

     // Updates pheromones based on the tour and fitness of the solution
    @Override
    public void updatePheromones(double[][] tau, int[] tour, double fitness, boolean[][] edgesUsed) {
        int n = tour.length;
        // Calculate the pheromone update based on the tour
        for (int i = 0; i < n - 1; i++) {
            int from = tour[i], to = tour[i + 1];
            tau[from][to] = Math.min((1 - rho) * tau[from][to] + rho, tauMax);
            tau[to][from] = tau[from][to];
        }
        // Update the pheromone for the edge between the last and first node in the tour
        int last = tour[n - 1], first = tour[0];
        tau[last][first] = Math.min((1 - rho) * tau[last][first] + rho, tauMax);
        tau[first][last] = tau[last][first];
        // Reduce pheromone levels for edges not used in the tour
         // This ensures that pheromones evaporate over time
        for (int i = 0; i < tau.length; i++) {
            for (int j = 0; j < tau.length; j++) {
                if (!edgesUsed[i][j]) {
                    tau[i][j] = Math.max((1 - rho) * tau[i][j], tauMin);
                }
            }
        }
    }

    @Override
    public void setEvaporationRate(double rho) {
        this.rho = rho;
    }


}
