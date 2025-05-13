package com.ea_framework.Algorithms;

import com.ea_framework.Operators.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.TSP2DConfig;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Operators.MutationFunctions.MutationOperator;
import com.ea_framework.Problems.TSP2DProblem;

public class TSPAlgorithm implements Algorithm<int[]> {

    private int[] currentSolution;
    private ChoiceFunction<int[], Double> choiceFunction;
    private Fitness<int[], Double> fitnessFunction;
    private MutationOperator<int[]> mutationOperator;

    private final TSP2DProblem problem;

    private final double[][] distanceMatrix;

    private double currentFitness = Double.MAX_VALUE;
    private double bestFitness = Double.MAX_VALUE;
    private int bestIteration = 0;

    public TSPAlgorithm(TSP2DProblem problem) {
        this.problem = problem;
        this.distanceMatrix = problem.getDistanceMatrix();
    }

    @Override
    public Algorithm<?> apply(AlgorithmConfig config) {
        if (!(config instanceof TSP2DConfig tspConfig)) {
            throw new IllegalArgumentException("Expected TSP2DConfig, got " + config.getClass().getSimpleName());
        }

        this.fitnessFunction = tspConfig.fitness();
        this.mutationOperator = tspConfig.mutation();
        this.choiceFunction = tspConfig.choice();

        return this;
    }

    @Override
    public void run(int iteration) {
        int[] candidate = mutationOperator.mutate(deepCopy(currentSolution));

        double fitnessOriginal = evalFitness(currentSolution);
        double fitnessCandidate = evalFitness(candidate);

        currentSolution = choiceFunction.choose(currentSolution, candidate, fitnessOriginal, fitnessCandidate, iteration);
        currentFitness = evalFitness(currentSolution);

        if (currentFitness < bestFitness) {
            bestFitness = currentFitness;
            bestIteration = iteration;
        }
    }

    @Override
    public void setCurrentSolution(int[] defaultPermutation) {
        this.currentSolution = defaultPermutation;
    }

    @Override
    public int[] getCurrentSolution() {
        return currentSolution;
    }

    @Override
    public void setFirst(int[] first) {
        this.currentSolution = first;
    }

    @Override
    public Double getCurrentFitness() {
        return currentFitness;
    }

    @Override
    public Double getBestFitness() {
        return bestFitness;
    }

    @Override
    public int getBestIteration() {
        return bestIteration;
    }

    public int [] deepCopy(int[] array) {
        int[] copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        return copy;
    }

    private double evalFitness(int[] permutation) {
        return fitnessFunction.evaluate(permutation);
    }
}