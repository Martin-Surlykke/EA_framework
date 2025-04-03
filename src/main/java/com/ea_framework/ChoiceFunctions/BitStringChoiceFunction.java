package com.ea_framework.ChoiceFunctions;

public abstract class BitStringChoiceFunction implements ChoiceFunction<boolean[], Integer> {

    @Override
    public abstract boolean accept(
            boolean[] currentSolution,
            boolean[] candidateSolution,
            Integer currentFitness,
            Integer candidateFitness,
            int iteration);

    public boolean[] chooseCandidate(
            boolean[] currentSolution,
            boolean[] candidateSolution,
            Integer currentFitness,
            Integer candidateFitness,
            int iteration) {

        return accept(currentSolution, candidateSolution, currentFitness, candidateFitness, iteration)
                ? candidateSolution
                : currentSolution;
    }
}