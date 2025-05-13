package com.ea_framework.Operators.ChoiceFunctions;

import com.ea_framework.Configs.OperatorConfigs.BitStringChoiceConfig;
import com.ea_framework.Configurable;

import java.util.Random;

public class BitStringSimulatedAnnealing implements ChoiceFunction<boolean[], Integer>, Configurable<BitStringChoiceConfig> {

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

    @Override
    public void configure(BitStringChoiceConfig config) {

    }
}
