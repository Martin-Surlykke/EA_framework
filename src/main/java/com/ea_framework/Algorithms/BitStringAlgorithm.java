package com.ea_framework.Algorithms;

import com.ea_framework.Candidates.Candidate;
import com.ea_framework.ChoiceFunctions.BitStringChoiceFunction;
import com.ea_framework.FitnessFunctions.BitStringFitness;
import com.ea_framework.Mutation.BitStringMutationOperator;

import java.util.Arrays;

public class BitStringAlgorithm extends Algorithms {
    protected boolean [] currentSolution;
    private final BitStringChoiceFunction choiceFunction;

    private final BitStringFitness fitnessFunction;

    private final BitStringMutationOperator mutationOperator;

    int currentFitness;

    public BitStringAlgorithm(int maxIterations,
                              BitStringFitness fitnessFunction,
                              BitStringMutationOperator mutationOperator,
                              BitStringChoiceFunction choiceFunction) {
        super(maxIterations);
        this.choiceFunction = choiceFunction;
        this.fitnessFunction = fitnessFunction;
        this.mutationOperator = mutationOperator;
        currentFitness = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < maxIterations; i++) {
            boolean [] copy = deepCopy(currentSolution);
            mutationOperator.mutate(copy);

            currentSolution = choiceFunction(currentSolution, copy, i);
            currentFitness = evalFitness(currentSolution);

            System.out.println("Fitness: " + currentFitness + " Iteration: " + i);

        }

    }

    public boolean[] choiceFunction(boolean[] currentSolution, boolean[] copy, int iteration) {
        return choiceFunction.chooseCandidate(currentSolution,copy,evalFitness(currentSolution),evalFitness(copy),iteration);
    }

    public int evalFitness(boolean[] permutation) {
        return fitnessFunction.evaluate(permutation);
    }

    public boolean [] getCurrentSolution() {
        return currentSolution;
    }

    public void setCurrentSolution(boolean[] currentSolution) {
        this.currentSolution = currentSolution;
        currentFitness = evalFitness(currentSolution);
    }

    private static boolean[] deepCopy(boolean[] bitString) {
        boolean [] copy = new boolean[bitString.length];
        System.arraycopy(bitString, 0, copy, 0, bitString.length);
        return copy;
    }

}
