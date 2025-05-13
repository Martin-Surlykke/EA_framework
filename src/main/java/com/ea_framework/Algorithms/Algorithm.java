package com.ea_framework.Algorithms;

import com.ea_framework.Configs.AlgorithmConfig;
import com.sun.jdi.Value;

public interface Algorithm<SolutionType> {
    void run(int iteration);
    SolutionType getCurrentSolution();
    void setFirst(SolutionType first);

    Algorithm<?> apply(AlgorithmConfig config);

    void setCurrentSolution(SolutionType defaultPermutation);

    Double getCurrentFitness();

    Double getBestFitness();

    int getBestIteration();

    default SolutionType getSolution(SolutionType solution) {
        return solution;
    }
}
