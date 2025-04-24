package com.ea_framework.FitnessFunctions;

public class TspEuclidianDistance implements Fitness<DistanceMatrixContext<int[]>, Double> {

    @Override
    public Double evaluate(DistanceMatrixContext<int[]> context) {
        int[] tour = context.input();
        double[][] matrix = context.distanceMatrix();

        double cost = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            cost += matrix[tour[i]][tour[i + 1]];
        }
        cost += matrix[tour[tour.length - 1]][tour[0]];
        return cost;
    }
}
