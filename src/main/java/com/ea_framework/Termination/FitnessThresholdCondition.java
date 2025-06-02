package com.ea_framework.Termination;

import com.ea_framework.Algorithms.Algorithm;

public class FitnessThresholdCondition implements TerminationCondition {
    private final double threshold;

    public FitnessThresholdCondition(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public boolean shouldTerminate(int iteration, Algorithm algorithm) {
        return algorithm.getCurrentFitness() >= threshold;
    }
}
