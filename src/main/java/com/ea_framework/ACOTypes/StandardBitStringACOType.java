package com.ea_framework.ACOTypes;

public class StandardBitStringACOType implements BitStringACOType {

    private final double rho = 0.1;
    private final double Q = 1.0;

    @Override
    public void initializePheromones(double[][] pheromones, int length, double initialPheromone) {
        for (int i = 0; i < length; i++) {
            pheromones[i][0] = initialPheromone;
            pheromones[i][1] = initialPheromone;
        }
    }

    @Override
    public void updatePheromones(double[][] pheromones, boolean[] solution, double fitness) {
        for (int i = 0; i < solution.length; i++) {
            pheromones[i][0] *= (1 - rho);
            pheromones[i][1] *= (1 - rho);
            pheromones[i][solution[i] ? 1 : 0] += Q / fitness;
        }
    }
}
