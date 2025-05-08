package com.ea_framework.Configs;

import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.FitnessFunctions.DistanceMatrixContext;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.Mutation.MutationOperator;

public record TSP2DAlgorithmConfig(Fitness<DistanceMatrixContext<int[]>, Double> fitness, MutationOperator<int[]> mutation,
                                   ChoiceFunction<int[], Double> choice, double[][] distanceMatrix) implements AlgorithmConfig {
}
