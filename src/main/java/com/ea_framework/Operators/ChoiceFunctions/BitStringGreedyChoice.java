package com.ea_framework.Operators.ChoiceFunctions;

import com.ea_framework.Configs.OperatorConfigs.BitStringChoiceConfig;
import com.ea_framework.Configurable;
import com.ea_framework.Problems.BitStringProblem;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public class BitStringGreedyChoice implements ChoiceFunction, Configurable {

    @Override
    public Object choose(Object current, Object candidate, Object fitnessCurrent, Object fitnessCandidate, int iteration) {
        if (!(current instanceof boolean[] curr &&
                candidate instanceof boolean[] cand &&
                fitnessCurrent instanceof Integer currFitness &&
                fitnessCandidate instanceof Integer candFitness)) {
            throw new IllegalArgumentException("BitStringGreedyChoice received incompatible types.");
        }

        return candFitness > currFitness ? cand : curr;
    }

    @Override
    public void configure(Map<String, Object> config) {

    }
}
