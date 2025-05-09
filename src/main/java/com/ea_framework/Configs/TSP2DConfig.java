package com.ea_framework.Configs;

import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.FitnessFunctions.DistanceMatrixContext;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.MutationFunctions.MutationOperator;

public record TSP2DConfig(Fitness<int[], Double> fitness, MutationOperator<int[]> mutation,
                          ChoiceFunction<int[], Double> choice) implements AlgorithmConfig {
}
