package com.ea_framework.Algorithms;

import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.BitStringGenericAlgorithmConfig;
import com.ea_framework.Operators.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Operators.MutationFunctions.MutationOperator;

public class BitStringGenericAlgorithm implements Algorithm {

    private boolean[] currentSolution;

    private ChoiceFunction choiceFunction;
    private Fitness fitnessFunction;
    private MutationOperator mutationOperator;

    private int currentFitness = 0;
    private int bestFitness = 0;
    private int bestIteration = 0;


    // Constructor to initialize with a default solution, and chosen operators
    @Override
    public void apply(AlgorithmConfig config) {
        if (!(config instanceof BitStringGenericAlgorithmConfig bitConfig)) {
            throw new IllegalArgumentException("Expected BitStringGenericAlgorithmConfig, got " + config.getClass().getSimpleName());
        }
        this.mutationOperator = bitConfig.mutation();
        this.choiceFunction = bitConfig.choice();
        this.fitnessFunction = bitConfig.fitness();

    }


    // Run the algorithm for a given iteration
    @Override
    public void run(int iteration) {
        // Ensure currentSolution is initialized
        Object mutatedObj = mutationOperator.mutate(deepCopy(currentSolution));
        if (!(mutatedObj instanceof boolean[] mutated)) {
            throw new IllegalStateException("Mutation operator must return boolean[]");
        }

        // Evaluate fitness of current and mutated solutions
        double fitnessCurrent = evalFitness(currentSolution);
        double fitnessCandidate = evalFitness(mutated);


        // Use the choice function to decide which solution to keep
        Object chosen = choiceFunction.choose(currentSolution, mutated, fitnessCurrent, fitnessCandidate, iteration);
        if (!(chosen instanceof boolean[] chosenBits)) {
            throw new IllegalStateException("Choice function must return boolean[]");
        }

        // Update current solution and fitness
        currentSolution = chosenBits;
        currentFitness = (int) evalFitness(currentSolution);

        // Update best fitness and iteration if current is better
        if (currentFitness > bestFitness) {
            bestFitness = currentFitness;
            bestIteration = iteration;
        }

    }


    // Getters and setters for current solution and fitness values
    @Override
    public Object getCurrentSolution() {
        return currentSolution;
    }

    @Override
    public void setCurrentSolution(Object solution) {
        if (!(solution instanceof boolean[] bits)) {
            throw new IllegalArgumentException("Expected boolean[] for BitStringAlgorithm");
        }
        this.currentSolution = bits;
        this.currentFitness = (int) evalFitness(bits);
    }

    @Override
    public Double getCurrentFitness() {
        return (double) currentFitness;
    }

    @Override
    public Double getBestFitness() {
        return (double) bestFitness;
    }

    @Override
    public int getBestIteration() {
        return bestIteration;
    }

    private double evalFitness(boolean[] bitString) {
        Object result = fitnessFunction.evaluate(bitString);
        if (!(result instanceof Number n)) {
            throw new IllegalStateException("Fitness function must return a number");
        }
        return n.doubleValue();
    }

    private static boolean[] deepCopy(boolean[] bitString) {
        boolean[] copy = new boolean[bitString.length];
        System.arraycopy(bitString, 0, copy, 0, bitString.length);
        return copy;
    }
}
