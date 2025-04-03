package com.ea_framework.ChoiceFunctions;

import java.util.Random;

public class BitStringSimulatedAnnealing extends BitStringChoiceFunction {

    private static double alpha;
    private static double T_0;

    private static final Random rand = new Random();
    @Override
    public boolean accept(boolean[] currentSolution,
                          boolean[] candidateSolution,
                          Integer currentFitness,
                          Integer candidateFitness,
                          int iteration) {

        if (candidateFitness > currentFitness) {
            return true;
        }

        double T_i = T_0*Math.pow(alpha, iteration);

        System.out.println("Checking Temperature: " + T_i);
        double probability = rand.nextDouble();
        double acceptance = Math.exp((candidateFitness - currentFitness) / T_i);

        return probability < acceptance;
    }

    public void setAlpha(double a) {
        alpha = a;
    }

    public void setT0(double t) {
        T_0 = t;
    }
}
