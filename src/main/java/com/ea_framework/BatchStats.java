package com.ea_framework;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.BatchConfig;
import com.ea_framework.Problems.Problem;

public class BatchStats {
    private final String problem;
    private final String algorithm;
    private final int problemSize;
    private final double bestFitness;
    private final int bestIteration;
    private final long bestEvaluation;
    private final long runtimeMs;

    private final Problem problemInstance;

    public BatchStats(String problem, String algorithm, Problem problemInstance,
                      double bestFitness, int bestIteration, long bestEvaluation, long runtimeMs) {
        this.problem = problem;
        this.algorithm = algorithm;
        this.problemSize = problemInstance.getSize();
        this.bestFitness = bestFitness;
        this.bestIteration = bestIteration;
        this.bestEvaluation = bestEvaluation;
        this.runtimeMs = runtimeMs;
        this.problemInstance = problemInstance;
    }

    public static BatchStats from(BatchConfig config, Problem problem, Algorithm algo, long runtimeMs) {
        return new BatchStats(
                config.getProblemName(),
                config.getAlgorithmName(),
                problem.getInstance(),
                algo.getBestFitness(),
                algo.getBestIteration(),
                algo.getBestIteration() * 2,  // NOTE: change this if evals != 2*iters
                runtimeMs
        );
    }

    public String getProblem() {
        return problem;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public int getBestIteration() {
        return bestIteration;
    }

    public long getBestEvaluation() {
        return bestEvaluation;
    }

    public long getRuntimeMs() {
        return runtimeMs;
    }

    public int getProblemSize() {
        return problemSize;  // assumes `Problem` has a getSize() method
    }
}
