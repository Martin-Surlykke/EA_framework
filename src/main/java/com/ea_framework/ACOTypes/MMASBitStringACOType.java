package com.ea_framework.ACOTypes;

public class MMASBitStringACOType implements BitStringACOType {

    // Max-Min Ant System (MMAS) for Bit String ACO

    // Evaporation rate for pheromone trails
    private final double evaporationRate;
    // Q value for pheromone update
    private final double Q;
// Minimum and maximum pheromone levels
    private final double tauMin;
    private final double tauMax;


    // Constructor to initialize MMAS parameters
    public MMASBitStringACOType(double evaporationRate, double Q, double tauMin, double tauMax) {
        this.evaporationRate = evaporationRate;
        this.Q = Q;
        this.tauMin = tauMin;
        this.tauMax = tauMax;
    }

    // Initializer for pheromones in construction graph
    @Override
    public void initializePheromones(double[][] pheromones, int length, double initialPheromone) {
        for (int i = 0; i < length; i++) {
            pheromones[i][0] = initialPheromone;
            pheromones[i][1] = initialPheromone;
        }
    }


    // Method to update pheromones based on the solution found
    @Override
    public void updatePheromones(double[][] pheromones, boolean[] solution, double fitness) {
        for (int i = 0; i < solution.length; i++) {
            pheromones[i][0] *= (1 - evaporationRate);
            pheromones[i][1] *= (1 - evaporationRate);

            // Update pheromone levels based on the solution
            int bit = solution[i] ? 1 : 0;
            pheromones[i][bit] += Q / fitness;


            // Clamp pheromone levels to the defined min and max
            pheromones[i][0] = clamp(pheromones[i][0], tauMin, tauMax);
            pheromones[i][1] = clamp(pheromones[i][1], tauMin, tauMax);
        }
    }

    // Method to select the next bit based on pheromone levels and heuristic information
    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
