package com.ea_framework.Operators.ChoiceFunctions;

import com.ea_framework.Configs.OperatorConfigs.TSP2DChoiceConfig;
import com.ea_framework.Configurable;

public class TSP2DGreedyChoice implements ChoiceFunction<int [], Double>, Configurable<TSP2DChoiceConfig> {

    @Override
    public void configure(TSP2DChoiceConfig config) {

    }

    @Override
    public int[] choose(int[] current, int[] candidate, Double fitnessCurrent, Double fitnessCandidate, int iteration) {
        return fitnessCandidate < fitnessCurrent ? candidate : current;
    }
}