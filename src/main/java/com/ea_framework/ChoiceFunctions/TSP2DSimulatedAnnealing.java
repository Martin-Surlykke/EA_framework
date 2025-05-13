package com.ea_framework.ChoiceFunctions;

import java.util.Comparator;
import java.util.Random;

public class TSP2DSimulatedAnnealing<SolutionType, FitnessType extends Number> implements ChoiceFunction<SolutionType, FitnessType> {
    private final Comparator<FitnessType> fitnessComparator;
    private final double alpha;
    private final double T_0;

    private static final Random rand = new Random();

    public TSP2DSimulatedAnnealing(Comparator<FitnessType> fitnessComparator, double alpha, double T_0) {
        this.fitnessComparator = fitnessComparator;
        this.alpha = alpha;
        this.T_0 = T_0;
    }

    @Override
    public SolutionType choose(SolutionType current,
                               SolutionType candidate,
                               FitnessType currentFitness,
                               FitnessType candidateFitness,
                               int iteration) {

        if (fitnessComparator.compare(currentFitness, candidateFitness) < 0) {
            return candidate;
        }

        double T_i = T_0 * Math.pow((1-alpha), iteration);
        double probability = rand.nextDouble();
        double acceptance = Math.exp((currentFitness.doubleValue() - candidateFitness.doubleValue()) / T_i);

        return probability < acceptance ? candidate : current;
    }
}