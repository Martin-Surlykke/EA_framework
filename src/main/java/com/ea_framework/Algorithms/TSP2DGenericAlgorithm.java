package com.ea_framework.Algorithms;

import com.ea_framework.Operators.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.TSP2DGenericAlgorithmConfig;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Operators.MutationFunctions.MutationOperator;

public class TSP2DGenericAlgorithm implements Algorithm {

    private int[] currentSolution;

    private ChoiceFunction choiceFunction;
    private Fitness fitnessFunction;
    private MutationOperator mutationOperator;

    private double currentFitness = Double.MAX_VALUE;
    private double bestFitness = Double.MAX_VALUE;
    private int bestIteration = 0;

    @Override
    public void apply(AlgorithmConfig config) {
        if (!(config instanceof TSP2DGenericAlgorithmConfig tspConfig)) {
            throw new IllegalArgumentException("Expected TSP2DConfig, got " + config.getClass().getSimpleName());
        }

        this.fitnessFunction = tspConfig.fitness();
        this.mutationOperator = tspConfig.mutation();
        this.choiceFunction = tspConfig.choice();

    }

    @Override
    public void run(int iteration) {
        Object candidateObj = mutationOperator.mutate(deepCopy(currentSolution));
        if (!(candidateObj instanceof int[] candidate)) {
            throw new IllegalStateException("Mutation did not return int[]");
        }

        double fitnessOriginal = evalFitness(currentSolution);
        double fitnessCandidate = evalFitness(candidate);

        Object chosen = choiceFunction.choose(currentSolution, candidate, fitnessOriginal, fitnessCandidate, iteration);
        if (!(chosen instanceof int[] chosenTour)) {
            throw new IllegalStateException("Choice function did not return int[]");
        }

        currentSolution = chosenTour;
        currentFitness = evalFitness(currentSolution);

        if (currentFitness < bestFitness) {
            bestFitness = currentFitness;
            bestIteration = iteration;
        }
    }

    @Override
    public void setCurrentSolution(Object defaultPermutation) {
        if (!(defaultPermutation instanceof int[] ints)) {
            throw new IllegalArgumentException("Expected int[] for TSPAlgorithm");
        }
        this.currentSolution = ints;
    }

    @Override
    public Object getCurrentSolution() {
        return currentSolution;
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

    private int[] deepCopy(int[] array) {
        int[] copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        return copy;
    }

    private double evalFitness(int[] permutation) {
        Object result = fitnessFunction.evaluate(permutation);
        if (!(result instanceof Double f)) {
            throw new IllegalStateException("Fitness function did not return Double");
        }
        return f;
    }
}
