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

        System.out.println("Alpha: " + alpha + " T0: " + T_0 + " Iteration: " + iteration);

        return (probability < acceptance) ? cand : curr;
    }

    @Override
    public void configure(Map<String, Object> config) {
        this.alpha = Double.parseDouble(config.get("alpha").toString());
        this.T_0 = Double.parseDouble(config.get("t0").toString());
    }
}
