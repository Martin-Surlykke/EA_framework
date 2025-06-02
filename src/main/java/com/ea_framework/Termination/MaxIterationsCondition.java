package com.ea_framework.Termination;

import com.ea_framework.Algorithms.Algorithm;

public class MaxIterationsCondition implements TerminationCondition {
    private final int maxIterations;

    public MaxIterationsCondition(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public boolean shouldTerminate(int iteration, Algorithm algorithm) {
        return iteration >= maxIterations;
    }
}
