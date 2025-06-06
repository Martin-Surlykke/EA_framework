package com.ea_framework.ACOTypes;

public interface BitStringACOType {
    // This interface defines the methods required for a BitString ACO type.
    void initializePheromones(double[][] pheromones, int length, double initialPheromone);
    // Initializes the pheromone matrix with a given initial value.
    void updatePheromones(double[][] pheromones, boolean[] solution, double fitness);
    // Updates the pheromone matrix based on the solution and its fitness.
}
