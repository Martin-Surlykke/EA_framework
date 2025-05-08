package com.ea_framework.Algorithms;

import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.FitnessFunctions.DistanceMatrixContext;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.Mutation.MutationOperator;
import com.ea_framework.Mutation.TwoOptTsp;
import com.ea_framework.View.Viewables.TSPViewable;

public class TSPAlgorithm implements Algorithms<int []>, TSPViewable {

    protected int [] currentSolution;
    private final ChoiceFunction <int[], Double> choiceFunction;

    private final Fitness<DistanceMatrixContext<int[]>, Double> fitnessFunction;

    private final MutationOperator<int[]> mutationOperator;

    private final double [][] DistanceMatrix;

    double currentFitness;

    double bestFitness;

    int bestIteration;

    public TSPAlgorithm(Fitness<DistanceMatrixContext<int[]>, Double> fitness,
                        MutationOperator<int[]> mutation,
                        ChoiceFunction<int[], Double> choice,
                        double [][] DM) {
        this.choiceFunction = choice;
        this.fitnessFunction = fitness;
        this.mutationOperator = mutation;
        this.DistanceMatrix = DM;
        currentFitness = Double.MAX_VALUE;
        bestFitness = Double.MAX_VALUE;
        bestIteration = 0;
    }
    @Override
    public void run(int iterations) {
        int[] copy = mutationOperator.mutate(deepCopy(currentSolution));

        currentSolution = choiceFunction.choose(currentSolution, copy, evalFitness(currentSolution), evalFitness(copy), iterations);
        if (currentFitness < bestFitness) {
            bestFitness = currentFitness;
            bestIteration = iterations;
        }
        currentFitness = evalFitness(currentSolution);

    }

    @Override
    public int [] getCurrentSolution() {
        return currentSolution;
    }

    @Override
    public void setFirst(int[] first) {

    }

    public double getCurrentFitness() {
        return currentFitness;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public int getBestIteration() {
        return bestIteration;
    }

    private static int[] deepCopy(int[] input) {
        return TwoOptTsp.deepCopyList(input);
    }

    public double evalFitness(int[] permutation) {
        return fitnessFunction.evaluate(new DistanceMatrixContext<>(permutation, DistanceMatrix));
    }

    public void setCurrentSolution(int[] permutation) {
        currentSolution = permutation;
    }

}
