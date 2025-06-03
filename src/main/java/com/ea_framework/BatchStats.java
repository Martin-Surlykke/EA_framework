package com.ea_framework;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.BatchConfig;

public class BatchStats {
    private final String problem;
    private final String algorithm;
    private final double bestFitness;
    private final int bestIteration;
    private final long bestEvaluation;
    private final long runtimeMs;

    private final int problemSize;

    public BatchStats(String problem, String algorithm, double bestFitness, int bestIteration, long bestEvaluation, long runtimeMs, int problemSize) {
        this.problem = problem;
        this.algorithm = algorithm;
        this.bestFitness = bestFitness;
        this.bestIteration = bestIteration;
        this.bestEvaluation = bestEvaluation;
        this.runtimeMs = runtimeMs;
        this.problemSize = problemSize;
    }

    public static BatchStats from(BatchConfig config, Algorithm algo, long runtimeMs) {
        return new BatchStats(
                config.getProblemName(),
                config.getAlgorithmName(),
                algo.getBestFitness(),
                algo.getBestIteration(),
                algo.getBestIteration()*2,
                runtimeMs,
                config.getProblemSize()
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
        return problemSize;
    }
}
