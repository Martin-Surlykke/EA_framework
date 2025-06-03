package com.ea_framework.Termination;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configurable;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public class MaxIterationsCondition implements TerminationCondition, Configurable {
    private int maxIterations = Integer.MAX_VALUE;

    public MaxIterationsCondition() {}

    public MaxIterationsCondition(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public boolean shouldTerminate(int iteration, Algorithm algorithm) {
        return iteration >= maxIterations;
    }

    @Override
    public void configure(Map<String, String> params) {
        if (params.containsKey("value")) {
            try {
                this.maxIterations = Integer.parseInt(params.get("value"));
            } catch (NumberFormatException e) {
                System.err.println("Invalid maxIterations value: " + params.get("value"));
            }
        }
    }

    @Override
    public void configure(Problem problem) {

    }
}
