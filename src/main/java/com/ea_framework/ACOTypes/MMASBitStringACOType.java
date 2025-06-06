package com.ea_framework.ACOTypes;

import java.util.Map;

public class MMASBitStringACOType implements BitStringACOType {

    private double evaporationRate;
    private double Q;
    private double tauMin;
    private double tauMax;
    private double initialPheromone;



    public void populate(double evaporationRate, double Q, double tauMin, double tauMax) {
        this.evaporationRate = evaporationRate;
        this.Q = Q;
        this.tauMin = tauMin;
        this.tauMax = tauMax;
        this.initialPheromone = (tauMin + tauMax) / 2.0;
    }

    @Override
    public void initializePheromones(double[][] pheromones, int length, double ignoredInitialPheromone) {
        for (int i = 0; i < length; i++) {
            pheromones[i][0] = initialPheromone;
            pheromones[i][1] = initialPheromone;
        }
    }

    @Override
    public void updatePheromones(double[][] pheromones, boolean[] solution, double fitness) {
        System.out.printf("→ Updating pheromones with fitness: %.4f\n", fitness);

        double total0 = 0, total1 = 0;

        for (int i = 0; i < solution.length; i++) {
            double before0 = pheromones[i][0];
            double before1 = pheromones[i][1];

            // Evaporation
            pheromones[i][0] *= (1 - evaporationRate);
            pheromones[i][1] *= (1 - evaporationRate);

            // Reinforcement
            int bit = solution[i] ? 1 : 0;
            double reward = Q / fitness;
            pheromones[i][bit] += reward / solution.length;

            // Clamp
            pheromones[i][0] = clamp(pheromones[i][0], tauMin, tauMax);
            pheromones[i][1] = clamp(pheromones[i][1], tauMin, tauMax);

            total0 += pheromones[i][0];
            total1 += pheromones[i][1];
        }

    }

    @Override
    public void setEvaporationRate(double rho) {
        this.evaporationRate = rho;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public void populate(Map<String, Object> raw) {
        this.evaporationRate = raw.get("evaporationRate") == null ? 0.1 : (double) raw.get("evaporationRate");
        this.initialPheromone = raw.get("initialPheromone") == null ? 0.5 : (double) raw.get("initialPheromone");
        this.initialPheromone = clamp(this.initialPheromone, tauMin, tauMax);
        // Assume `reinforcement` is Q
        this.initialPheromone = clamp(this.initialPheromone, tauMin, tauMax);
    }

}
