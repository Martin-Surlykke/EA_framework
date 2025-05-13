package com.ea_framework.Algorithms;

import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.MutationFunctions.MutationOperator;

public class BitStringAlgorithm implements Algorithm<boolean[]> {
    protected boolean[] currentSolution;
    private final ChoiceFunction<boolean[], Integer> choiceFunction;

    private final Fitness<boolean[], Integer> fitnessFunction;

    private final MutationOperator<boolean[]> mutationOperator;

    int currentFitness;

    public BitStringAlgorithm(Fitness<boolean[], Integer> fitness,
                              MutationOperator<boolean[]> mutation,
                              ChoiceFunction <boolean[], Integer> choice) {
        this.choiceFunction = choice;
        this.fitnessFunction = fitness;
        this.mutationOperator = mutation;
        currentFitness = 0;
    }

    @Override
    public void run(int i) {
            boolean[] copy = mutationOperator.mutate(deepCopy(currentSolution));

            currentSolution = choiceFunction.choose(currentSolution, copy, evalFitness(currentSolution), evalFitness(copy), i);
            currentFitness = evalFitness(currentSolution);

            System.out.println("Fitness: " + currentFitness + " Iteration: " + i);

    }

    @Override
    public boolean[] getCurrentSolution() {
        return currentSolution;
    }

    @Override
    public void setFirst(boolean[] start) {
        currentSolution = start;
    }

    @Override
    public Algorithm<?> apply(AlgorithmConfig config) {

        return this;
    }


    public int evalFitness(boolean[] permutation) {
        return fitnessFunction.evaluate(permutation);
    }


    public void setCurrentSolution(boolean[] currentSolution) {
        this.currentSolution = currentSolution;
        currentFitness = evalFitness(currentSolution);
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
