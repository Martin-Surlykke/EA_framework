package com.ea_framework.ChoiceFunctions;

import java.util.Random;

public class TSP2DSimulatedAnnealing implements ChoiceFunction<int[], Double> {
    private final double alpha;
    private final double T_0;

    private static final Random rand = new Random();

    public TSP2DSimulatedAnnealing(double alpha, double temperature) {
        this.alpha = alpha;
        this.T_0 = temperature;
    }

    @Override
    public int[] choose(int[] current, int[] candidate, Double currentFitness, Double candidateFitness, int iteration) {
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

}
