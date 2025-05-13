package com.ea_framework.Algorithms;

import com.ea_framework.Operators.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Operators.MutationFunctions.MutationOperator;

public class BitStringAlgorithm implements Algorithm {

    protected boolean[] currentSolution;
    private final ChoiceFunction choiceFunction;
    private final Fitness fitnessFunction;
    private final MutationOperator mutationOperator;

    private int currentFitness;

    public BitStringAlgorithm(Fitness fitness, MutationOperator mutation, ChoiceFunction choice) {
        this.choiceFunction = choice;
        this.fitnessFunction = fitness;
        this.mutationOperator = mutation;
        this.currentFitness = 0;
    }

    @Override
    public void run(int iteration) {
        Object mutatedObj = mutationOperator.mutate(deepCopy(currentSolution));
        if (!(mutatedObj instanceof boolean[] mutated)) {
            throw new IllegalStateException("Mutation operator must return boolean[]");
        }

        double fitnessCurrent = evalFitness(currentSolution);
        double fitnessCandidate = evalFitness(mutated);

        Object chosen = choiceFunction.choose(currentSolution, mutated, fitnessCurrent, fitnessCandidate, iteration);
        if (!(chosen instanceof boolean[] chosenBits)) {
            throw new IllegalStateException("Choice function must return boolean[]");
        }

        currentSolution = chosenBits;
        currentFitness = (int) evalFitness(currentSolution);

        System.out.println("Fitness: " + currentFitness + " Iteration: " + iteration);
    }

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
    public Algorithm apply(AlgorithmConfig config) {
        return this; // Not used since you manually pass all operators in constructor
    }

    private double evalFitness(boolean[] bitString) {
        Object result = fitnessFunction.evaluate(bitString);
        if (!(result instanceof Number n)) {
            throw new IllegalStateException("Fitness function must return a number");
        }
        return n.doubleValue();
    }

    @Override
    public Double getCurrentFitness() {
        return (double) currentFitness;
    }

    @Override
    public Double getBestFitness() {
        return (double) currentFitness;
    }

    @Override
    public int getBestIteration() {
        return 100;
    }

    private static boolean[] deepCopy(boolean[] bitString) {
        boolean[] copy = new boolean[bitString.length];
        System.arraycopy(bitString, 0, copy, 0, bitString.length);
        return copy;
    }
}
