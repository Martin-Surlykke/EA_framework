package com.ea_framework.Operators.ChoiceFunctions;

import com.ea_framework.Configs.OperatorConfigs.BitStringChoiceConfig;
import com.ea_framework.Configurable;
import com.ea_framework.Problems.BitStringProblem;
import com.ea_framework.Problems.Problem;

import java.util.Map;
import java.util.Random;

public class BitStringSimulatedAnnealing implements ChoiceFunction, Configurable {

    private double alpha;
    private double T_0;

    private static final Random rand = new Random();

    @Override
    public Object choose(Object current, Object candidate, Object currentFitness, Object candidateFitness, int iteration) {
        if (!(current instanceof boolean[] curr &&
                candidate instanceof boolean[] cand &&
                currentFitness instanceof Integer fCurr &&
                candidateFitness instanceof Integer fCand)) {
            throw new IllegalArgumentException("BitStringSimulatedAnnealing received incompatible types.");
        }

        if (fCand > fCurr) return cand;

        double T_i = T_0 * Math.pow(alpha, iteration);
        double probability = rand.nextDouble();
        double acceptance = Math.exp((double) (fCand - fCurr) / T_i);

        return probability < acceptance ? cand : curr;
    }

    @Override
    public void configure(Problem problem) {

    }
}
