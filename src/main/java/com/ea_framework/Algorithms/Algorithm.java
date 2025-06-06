package com.ea_framework.Algorithms;

import com.ea_framework.Configs.AlgorithmConfig;

// This interface defines the basic structure for an algorithm in the EA framework.
public interface Algorithm {

    // The function runs a given iteration of the algorithm. Simulating an iterative loop
    void run(int iteration);


    // Getters and setters for the current solution and fitness values
    Object getCurrentSolution();
    void setCurrentSolution(Object solution);


    // Getters for fitness values
    Double getCurrentFitness();
    Double getBestFitness();
    int getBestIteration();

    // Optional runtime configuration
    default void apply(AlgorithmConfig config) {
        throw new UnsupportedOperationException("apply(...) not implemented");
    }
}
