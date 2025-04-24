package com.ea_framework.ChoiceFunctions;

import java.util.Comparator;

public class GreedyChoice <SolutionType, FitnessType> implements ChoiceFunction<SolutionType, FitnessType> {
    private final Comparator<FitnessType> fitnessComparator;

    public GreedyChoice(Comparator<FitnessType> fitnessComparator) {
        this.fitnessComparator = fitnessComparator;
    }

    @Override
    public SolutionType choose(SolutionType current,
                               SolutionType candidate,
                               FitnessType currentFitness,
                               FitnessType candidateFitness,
                               int iteration) {
        return fitnessComparator.compare(candidateFitness, currentFitness) > 0 ? candidate : current;
    }
}
