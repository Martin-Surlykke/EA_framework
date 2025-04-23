package com.ea_framework.ChoiceFunctions;

public class tspChoiceFunction implements ChoiceFunction<int[][], Double> {

    @Override
    public boolean accept(int[][] currentSolution, int[][] candidateSolution, Double currentFitness, Double candidateFitness, int iteration) {
        return false;
    }
}
