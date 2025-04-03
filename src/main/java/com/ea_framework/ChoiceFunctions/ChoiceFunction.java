package com.ea_framework.ChoiceFunctions;

import com.ea_framework.FitnessFunctions.Fitness;

public interface ChoiceFunction <T, V extends Comparable<V>>{
    boolean accept(
            T currentSolution,
            T candidateSolution,
            V currentFitness,
            V candidateFitness,
            int iteration);
}

