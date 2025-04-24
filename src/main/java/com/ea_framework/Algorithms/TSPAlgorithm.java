package com.ea_framework.Algorithms;

import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.FitnessFunctions.DistanceMatrixContext;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.Mutation.MutationOperator;

public class TSPAlgorithm implements Algorithms<int []> {

    protected int [] currentSolution;
    private final ChoiceFunction <int[], Double> choiceFunction;

    private final Fitness<DistanceMatrixContext<int[]>, Double> fitnessFunction;

    private final MutationOperator<int[]> mutationOperator;

    private final double [][] DistanceMatrix;

    double currentFitness;

    public TSPAlgorithm(Fitness<DistanceMatrixContext<int[]>, Double> fitness,
                        MutationOperator<int[]> mutation,
                        ChoiceFunction<int[], Double> choice,
                        double [][] DM) {
        this.choiceFunction = choice;
        this.fitnessFunction = fitness;
        this.mutationOperator = mutation;
        this.DistanceMatrix = DM;
        currentFitness = 0.0;
    }
    @Override
    public void run(int iterations) {
        int[] copy = mutationOperator.mutate(deepCopy(currentSolution));

        currentSolution = choiceFunction.choose(currentSolution, copy, evalFitness(currentSolution), evalFitness(copy), iterations);
        currentFitness = evalFitness(currentSolution);

        System.out.println("Fitness: " + currentFitness + " Iteration: " + iterations);

    }

    @Override
    public int [] getResult() {
        return null;
    }

    @Override
    public void setFirst(int[] first) {

    }

    private static int[] deepCopy(int[] input) {
        int[] copy = new int[input.length];
        System.arraycopy(input, 0, copy, 0, input.length);
        return copy;
    }

    public double evalFitness(int[] permutation) {
        return fitnessFunction.evaluate(new DistanceMatrixContext<>(permutation, DistanceMatrix));
    }

    public void setCurrentSolution(int[] permutation) {
        currentSolution = permutation;
    }

    public int[] getCurrentSolution() {
        return currentSolution;
    }
}
