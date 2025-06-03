package com.ea_framework.ACOTypes;

public interface BitStringACOType {
    void initializePheromones(double[][] pheromones, int length, double initialPheromone);
    void updatePheromones(double[][] pheromones, boolean[] solution, double fitness);
}
