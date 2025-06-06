package com.ea_framework.ACOTypes;

public interface ACOType {

        // Interface for Ant Colony Optimization (ACO) types
        void initializePheromones(double[][] tau, int length);
        // Initializes pheromone levels for the ACO algorithm
        void updatePheromones(double[][] tau, int[] tour, double fitness, boolean[][] edgesUsed);
        // Updates pheromone levels based on the constructed tour and its fitness

        public void setEvaporationRate(double rho);
}
