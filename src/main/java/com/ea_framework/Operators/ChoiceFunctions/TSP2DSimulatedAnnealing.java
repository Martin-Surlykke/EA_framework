package com.ea_framework.Operators.ChoiceFunctions;

import com.ea_framework.Configs.OperatorConfigs.TSP2DChoiceConfig;
import com.ea_framework.Configurable;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Problems.TSP2DProblem;

import java.util.Map;
import java.util.Random;

public class TSP2DSimulatedAnnealing implements ChoiceFunction, Configurable {

    private double alpha;
    private double T_0;

    private static final Random rand = new Random();

    @Override
    public Object choose(Object current, Object candidate, Object currentFitness, Object candidateFitness, int iteration) {
        if (!(current instanceof int[] curr &&
                candidate instanceof int[] cand &&
                currentFitness instanceof Double currFitness &&
                candidateFitness instanceof Double candFitness)) {
            throw new IllegalArgumentException("TSP2DSimulatedAnnealing received incompatible types.");
        }

        if (candFitness < currFitness) return cand;

        double T_i = T_0 * Math.pow(1 - alpha, iteration);
        double probability = rand.nextDouble();
        double acceptance = Math.exp((currFitness - candFitness) / T_i);

        return (probability < acceptance) ? cand : curr;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
    public void setT0(double v) {
        this.T_0 = v;
    }

    @Override
    public void configure(Problem problem) {

    }
}
