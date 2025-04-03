package com.ea_framework.ChoiceFunctions;
public interface ChoiceFunction <T, V extends Comparable<V>>{
    boolean accept(
            T currentSolution,
            T candidateSolution,
            V currentFitness,
            V candidateFitness,
            int iteration);
}

