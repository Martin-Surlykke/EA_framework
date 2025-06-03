package com.ea_framework.ACOTypes;

public class MMASBitStringACOType implements BitStringACOType {

    private final double evaporationRate;
    private final double Q;
    private final double tauMin;
    private final double tauMax;

    public MMASBitStringACOType(double evaporationRate, double Q, double tauMin, double tauMax) {
        this.evaporationRate = evaporationRate;
        this.Q = Q;
        this.tauMin = tauMin;
        this.tauMax = tauMax;
    }

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
            pheromones[i][0] *= (1 - evaporationRate);
            pheromones[i][1] *= (1 - evaporationRate);

            int bit = solution[i] ? 1 : 0;
            pheromones[i][bit] += Q / fitness;

            pheromones[i][0] = clamp(pheromones[i][0], tauMin, tauMax);
            pheromones[i][1] = clamp(pheromones[i][1], tauMin, tauMax);
        }
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
