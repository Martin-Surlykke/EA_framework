package com.ea_framework.ACOTypes;

public class MMASACOType implements ACOType {

    private final double rho = 0.1;
    private double tauMax;
    private double tauMin;

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

    @Override
    public void updatePheromones(double[][] tau, int[] tour, double fitness, boolean[][] edgesUsed) {
        int n = tour.length;

        for (int i = 0; i < n - 1; i++) {
            int from = tour[i], to = tour[i + 1];
            tau[from][to] = Math.min((1 - rho) * tau[from][to] + rho, tauMax);
            tau[to][from] = tau[from][to];
        }

        int last = tour[n - 1], first = tour[0];
        tau[last][first] = Math.min((1 - rho) * tau[last][first] + rho, tauMax);
        tau[first][last] = tau[last][first];

        for (int i = 0; i < tau.length; i++) {
            for (int j = 0; j < tau.length; j++) {
                if (!edgesUsed[i][j]) {
                    tau[i][j] = Math.max((1 - rho) * tau[i][j], tauMin);
                }
            }
        }
    }
}
