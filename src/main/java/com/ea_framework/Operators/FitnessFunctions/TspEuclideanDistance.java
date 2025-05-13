package com.ea_framework.Operators.FitnessFunctions;

import com.ea_framework.Configs.OperatorConfigs.TSP2DFitnessConfig;
import com.ea_framework.Configurable;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Problems.TSP2DProblem;

import java.util.Map;

public class TspEuclideanDistance implements Fitness, Configurable {

    private double[][] distanceMatrix;

    @Override
    public Double evaluate(Object input) {
        if (!(input instanceof int[] tour)) {
            throw new IllegalArgumentException("TspEuclideanDistance expects int[] input.");
        }

        double cost = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            cost += distanceMatrix[tour[i]][tour[i + 1]];
        }
        cost += distanceMatrix[tour[tour.length - 1]][tour[0]];
        return cost;
    }

    @Override
    public void configure(Map<String, Object> config) {
        this.distanceMatrix = (double[][]) config.get("distanceMatrix");
    }
}
