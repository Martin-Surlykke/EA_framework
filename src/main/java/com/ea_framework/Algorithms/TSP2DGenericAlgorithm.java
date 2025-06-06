package com.ea_framework.Algorithms;

import com.ea_framework.Operators.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.TSP2DGenericAlgorithmConfig;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Operators.MutationFunctions.MutationOperator;

public class TSP2DGenericAlgorithm implements Algorithm {

    // Generic algorithm for solving TSP problems in 2D space
    private int[] currentSolution;


    // The choice function, fitness function, and mutation operator are used to evolve the solution
    private ChoiceFunction choiceFunction;
    private Fitness fitnessFunction;
    private MutationOperator mutationOperator;


    // Fitness values to track the current and best solutions
    private double currentFitness = Double.MAX_VALUE;
    private double bestFitness = Double.MAX_VALUE;
    private int bestIteration = 0;


    // Constructor to initialize with a default solution, and chosen operators
    @Override
    public void apply(AlgorithmConfig config) {
        if (!(config instanceof TSP2DGenericAlgorithmConfig tspConfig)) {
            throw new IllegalArgumentException("Expected TSP2DConfig, got " + config.getClass().getSimpleName());
        }

        this.fitnessFunction = tspConfig.fitness();
        this.mutationOperator = tspConfig.mutation();
        this.choiceFunction = tspConfig.choice();

    }


    // Run the algorithm for a given iteration
    @Override
    public void run(int iteration) {
        Object candidateObj = mutationOperator.mutate(deepCopy(currentSolution));
        if (!(candidateObj instanceof int[] candidate)) {
            throw new IllegalStateException("Mutation did not return int[]");
        }

        // Evaluate fitness of current and candidate solutions
        double fitnessOriginal = evalFitness(currentSolution);
        double fitnessCandidate = evalFitness(candidate);

        // Use the choice function to decide which solution to keep
        Object chosen = choiceFunction.choose(currentSolution, candidate, fitnessOriginal, fitnessCandidate, iteration);
        if (!(chosen instanceof int[] chosenTour)) {
            throw new IllegalStateException("Choice function did not return int[]");
        }

        // Update current solution and fitness
        currentSolution = chosenTour;
        currentFitness = evalFitness(currentSolution);

        // Update best fitness and iteration if current is better
        if (currentFitness < bestFitness) {
            bestFitness = currentFitness;
            bestIteration = iteration;
        }
    }


    // Getters and setters for current solution and fitness values
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
