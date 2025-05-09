package com.ea_framework.FitnessFunctions;

public class TspEuclidianDistance implements Fitness<int[], Double> {

    private final double[][] distanceMatrix;

    public TspEuclidianDistance(double[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }
    @Override
    public Double evaluate(int[] input) {
        int[] tour = input.clone();

        double cost = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            cost += distanceMatrix[tour[i]][tour[i + 1]];
        }
        cost += distanceMatrix[tour[tour.length - 1]][tour[0]];
        return cost;
    }
}
