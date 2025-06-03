package com.ea_framework.Operators.ChoiceFunctions;

import com.ea_framework.Configurable;
import com.ea_framework.Problems.Problem;

import java.util.Random;

public class BitStringSimulatedAnnealing implements ChoiceFunction, Configurable {

    private double alpha;
    private double T_0;

    private static final Random rand = new Random();

    @Override
    public Object choose(Object current, Object candidate, Object currentFitness, Object candidateFitness, int iteration) {
        if (!(current instanceof boolean[] curr &&
                candidate instanceof boolean[] cand &&
                currentFitness instanceof Number currFitness &&
                candidateFitness instanceof Number candFitness)) {
            throw new IllegalArgumentException("BitStringGreedyChoice received incompatible types.");
        }

        double fCurr = currFitness.doubleValue();
        double fCand = candFitness.doubleValue();

        if (fCand > fCurr) return cand;

        double T_i = T_0 * Math.pow((1-alpha), iteration);
        double probability = rand.nextDouble();
        double acceptance = Math.exp((fCand - fCurr) / T_i);

        return probability < acceptance ? cand : curr;

    }

    @Override
    public void configure(Problem problem) {

    }

    public void setAlpha(double alpha) {
        if (alpha < 0 || alpha > 1) {
            throw new IllegalArgumentException("Alpha must be in the range (0, 1).");
        }
        this.alpha = alpha;
    }

    public void setT0(double T_0) {
        if (T_0 <= 0) {
            throw new IllegalArgumentException("Initial temperature must be positive.");
        }
        this.T_0 = T_0;
    }
}
