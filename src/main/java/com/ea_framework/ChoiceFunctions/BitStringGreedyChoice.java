package com.ea_framework.ChoiceFunctions;

public class BitStringGreedyChoice extends BitStringChoiceFunction{

    @Override
    public boolean accept(boolean[] currentSolution, boolean[] candidateSolution, Integer currentFitness, Integer candidateFitness, int iteration) {
        return candidateFitness > currentFitness;
    }

}
