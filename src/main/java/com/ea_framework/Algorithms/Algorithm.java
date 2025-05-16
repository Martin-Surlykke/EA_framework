package com.ea_framework.Algorithms;

import com.ea_framework.Configs.AlgorithmConfig;

public interface Algorithm {

    void run(int iteration);

    Object getCurrentSolution();
    void setCurrentSolution(Object solution);

    Double getCurrentFitness();
    Double getBestFitness();
    int getBestIteration();

    // Optional runtime configuration
    default void apply(AlgorithmConfig config) {
        throw new UnsupportedOperationException("apply(...) not implemented");
    }
}
