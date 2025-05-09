package com.ea_framework.Algorithms;

import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.Configs.TSP2DConfig;
import com.ea_framework.FitnessFunctions.DistanceMatrixContext;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.MutationFunctions.MutationOperator;
import com.ea_framework.MutationFunctions.TwoOptTsp;
import com.ea_framework.Problems.TSP2DProblem;
import com.ea_framework.Views.Viewables.TSPViewable;

public class TSPAlgorithm implements Algorithm<int []>, TSPViewable {

    protected int [] currentSolution;
    private final ChoiceFunction <int[], Double> choiceFunction;

    private final Fitness<int[], Double> fitnessFunction;

    private final MutationOperator<int[]> mutationOperator;
    double currentFitness;

    double bestFitness;

    int bestIteration;

    public TSPAlgorithm(TSP2DConfig config) {
        this.choiceFunction = config.choice();
        this.fitnessFunction = config.fitness();
        this.mutationOperator = config.mutation();
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
        return fitnessFunction.evaluate(permutation);
    }

    public void setCurrentSolution(int[] permutation) {
        currentSolution = permutation;
    }

}
