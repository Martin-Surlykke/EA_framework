package com.ea_framework.Operators.FitnessFunctions;

import com.ea_framework.Configs.OperatorConfigs.TSP2DFitnessConfig;
import com.ea_framework.Configurable;

public class TspEuclideanDistance implements Fitness<int[], Double>, Configurable<TSP2DFitnessConfig> {

    private double[][] distanceMatrix;

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

    @Override
    public void configure(TSP2DFitnessConfig config) {
        this.distanceMatrix = config.getDistanceMatrix();
    }
}
