package com.ea_framework.ACOTypes;

public interface ACOType {

        void initializePheromones(double[][] tau, int length);
        void updatePheromones(double[][] tau, int[] tour, double fitness, boolean[][] edgesUsed);
}
