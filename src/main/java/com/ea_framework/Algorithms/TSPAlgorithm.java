package com.ea_framework.Algorithms;

import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.TSP2DConfig;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.FitnessFunctions.TspEuclidianDistance;
import com.ea_framework.MutationFunctions.MutationOperator;
import com.ea_framework.MutationFunctions.TSP2DTwoOpt;

public class TSPAlgorithm implements Algorithm<int[]> {

    private int[] currentSolution;
    private ChoiceFunction<int[], Double> choiceFunction;
    private Fitness<int[], Double> fitnessFunction;
    private MutationOperator<int[]> mutationOperator;

    private double currentFitness = Double.MAX_VALUE;
    private double bestFitness = Double.MAX_VALUE;
    private int bestIteration = 0;

    public TSPAlgorithm() {
    }

    public Algorithm<?> apply(AlgorithmConfig config) {
        if (config instanceof TSP2DConfig tspConfig) {
            this.fitnessFunction = tspConfig.fitness();
            this.mutationOperator = tspConfig.mutation();
            this.choiceFunction = tspConfig.choice();
            return this;
        } else {
            throw new IllegalArgumentException("Expected TSP2DConfig, got " + config.getClass().getSimpleName());
        }
    }

    @Override
    public void setCurrentSolution(int[] defaultPermutation) {
        this.currentSolution = defaultPermutation;
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

    @Override
    public void run(int iteration) {
        int[] candidate = mutationOperator.mutate(deepCopy(currentSolution));

        double fitnessOriginal = evalFitness(currentSolution);
        double fitnessCandidate = evalFitness(candidate);

        currentSolution = choiceFunction.choose(currentSolution, candidate, fitnessOriginal, fitnessCandidate, iteration);
        currentFitness = evalFitness(currentSolution);

        if (currentFitness < bestFitness) {
            bestFitness = currentFitness;
            bestIteration = iteration;
        }
    }

    @Override
    public int[] getCurrentSolution() {
        return currentSolution;
    }


    @Override
    public void setFirst(int[] first) {
        this.currentSolution = first;
    }

    public double evalFitness(int[] permutation) {
        return fitnessFunction.evaluate(permutation);
    }

    public void setDistanceMatrix(double[][] distanceMatrix) {
        if (fitnessFunction instanceof TspEuclidianDistance tspDist) {
            tspDist.setDistanceMatrix(distanceMatrix);
        }
    }

    private static int[] deepCopy(int[] input) {
        return TSP2DTwoOpt.deepCopyList(input);
    }


}
