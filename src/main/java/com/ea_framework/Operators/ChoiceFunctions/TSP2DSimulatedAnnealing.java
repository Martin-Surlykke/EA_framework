package com.ea_framework.Operators.ChoiceFunctions;

import com.ea_framework.Configs.OperatorConfigs.TSP2DChoiceConfig;
import com.ea_framework.Configurable;

import java.util.Comparator;
import java.util.Random;

public class TSP2DSimulatedAnnealing implements ChoiceFunction<int [], Double>, Configurable<TSP2DChoiceConfig> {
    private double alpha;
    private double T_0;

    private static final Random rand = new Random();

    @Override
    public void configure(TSP2DChoiceConfig config) {
        this.alpha = config.getAlpha();
        this.T_0 = config.getT0();
    }

    @Override
    public int[] choose(int[] current, int[] candidate, Double currentFitness, Double candidateFitness, int iteration) {
        if (candidateFitness < currentFitness) {
            return candidate;
        }


        double T_i = T_0 * Math.pow((1-alpha), iteration);
        double probability = rand.nextDouble();
        double acceptance = Math.exp((currentFitness.doubleValue() - candidateFitness.doubleValue()) / T_i);

        return probability < acceptance ? candidate : current;
    }
}