package com.ea_framework.ChoiceFunctions;

import java.util.Random;

public class BitStringSimulatedAnnealing implements ChoiceFunction<boolean[], Integer> {

    private double alpha;
    private double T_0;

    private static final Random rand = new Random();

    @Override
    public boolean[] choose(boolean[] current, boolean[] candidate, Integer currentFitness, Integer candidateFitness, int iteration) {
        if (candidateFitness > currentFitness) {
            return candidate;
        }

        double T_i = T_0 * Math.pow(alpha, iteration);
        double probability = rand.nextDouble();
        double acceptance = Math.exp((candidateFitness - currentFitness) / T_i);

        System.out.println("Current fitness: " + currentFitness + ", candidate fitness: " + candidateFitness);
        System.out.println("Probability: " + probability + ", acceptance: " + acceptance);

        return probability < acceptance ? candidate : current;
    }

    public void setAlpha(double a) {
        alpha = a;
    }

    public void setT0(double t) {
        T_0 = t;
    }
}
