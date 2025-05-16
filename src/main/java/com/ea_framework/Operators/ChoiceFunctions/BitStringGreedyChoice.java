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
                fitnessCurrent instanceof Number currFitness &&
                fitnessCandidate instanceof Number candFitness)) {
            throw new IllegalArgumentException("BitStringGreedyChoice received incompatible types.");
        }

        double fCurr = currFitness.doubleValue();
        double fCand = candFitness.doubleValue();

        return fCand > fCurr ? cand : curr;
    }

    @Override
    public void configure(Problem problem) {

    }
}
