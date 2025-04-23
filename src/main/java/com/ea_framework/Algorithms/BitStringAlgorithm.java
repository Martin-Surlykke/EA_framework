package com.ea_framework.Algorithms;

import com.ea_framework.ChoiceFunctions.BitStringGreedyChoice;
import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.FitnessFunctions.BitStringLeadingOnes;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.Mutation.MutationOperator;
import com.ea_framework.Mutation.RLSBitString;
import com.ea_framework.StartAlgorithms.LoadPermutationBitString;
import com.ea_framework.StartAlgorithms.StartAlgorithm;
import com.ea_framework.StartAlgorithms.TspFromStartToEnd;

public class BitStringAlgorithm implements Algorithms<boolean[]> {
    private static int MAX_ITERATIONS;

    private static int iterations = 0;

    protected boolean[] currentSolution;
    private final ChoiceFunction<boolean[], Integer> choiceFunction;

    private final Fitness<boolean[], Integer> fitnessFunction;

    private final MutationOperator<boolean[]> mutationOperator;

    int currentFitness;

    public BitStringAlgorithm(BitStringLeadingOnes fitnessFunction,
                              RLSBitString mutationOperator, BitStringGreedyChoice choiceFunction) {
        this.choiceFunction = choiceFunction;
        this.fitnessFunction = fitnessFunction;
        this.mutationOperator = mutationOperator;
        currentFitness = 0;
    }

    @Override
    public void run() {
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            boolean[] copy = mutationOperator.mutate(deepCopy(currentSolution));

            currentSolution = choiceFunction.choose(currentSolution, copy, evalFitness(currentSolution), evalFitness(copy), i);
            currentFitness = evalFitness(currentSolution);

            System.out.println("Fitness: " + currentFitness + " Iteration: " + iterations);
            iterations++;

        }

    }

    @Override
    public boolean[] getResult() {
        return currentSolution;
    }

    @Override
    public void setFirst(boolean[] start) {
        currentSolution = start;
    }

    @Override
    public int getIterations() {
        return iterations;
    }

    @Override
    public void setMaxIterations(int maxIterations) {
        MAX_ITERATIONS = maxIterations;

    }


    public int evalFitness(boolean[] permutation) {
        return fitnessFunction.evaluate(permutation);
    }

    public boolean[] getCurrentSolution() {
        return currentSolution;
    }

    public void setCurrentSolution(boolean[] currentSolution) {
        this.currentSolution = currentSolution;
        currentFitness = evalFitness(currentSolution);
    }

    private static boolean[] deepCopy(boolean[] bitString) {
        boolean[] copy = new boolean[bitString.length];
        System.arraycopy(bitString, 0, copy, 0, bitString.length);
        return copy;
    }

}
