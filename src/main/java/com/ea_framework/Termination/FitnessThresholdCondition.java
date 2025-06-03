package com.ea_framework.Termination;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configurable;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public class FitnessThresholdCondition implements TerminationCondition, Configurable {

    private double threshold = Double.POSITIVE_INFINITY;
    private boolean isMaximization = true;  // Default to maximization unless specified

    public FitnessThresholdCondition() {}

    public FitnessThresholdCondition(double threshold, boolean isMaximization) {
        this.threshold = threshold;
        this.isMaximization = isMaximization;
    }

    @Override
    public boolean shouldTerminate(int iteration, Algorithm algorithm) {
        double fitness = algorithm.getCurrentFitness();
        if (isMaximization) {
            return fitness >= threshold;
        } else {
            return fitness <= threshold;
        }
    }

    @Override
    public void configure(Map<String, String> params) {
        if (params.containsKey("value")) {
            try {
                this.threshold = Double.parseDouble(params.get("value"));
            } catch (NumberFormatException e) {
                System.err.println("Invalid fitness threshold: " + params.get("value"));
            }
        }

        if (params.containsKey("mode")) {
            String mode = params.get("mode").trim().toLowerCase();
            this.isMaximization = mode.equals("maximize") || mode.equals("max");
        }
    }

    @Override
    public void configure(Problem problem) {
        // Optional: auto-detect mode from the problem class
    }
}
