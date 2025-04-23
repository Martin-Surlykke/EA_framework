package com.ea_framework.ChoiceFunctions;
public interface ChoiceFunction<SolutionType, FitnessType> {
    SolutionType choose(SolutionType current, SolutionType candidate,
                        FitnessType fitnessCurrent, FitnessType fitnessCandidate,
                        int iteration);
}