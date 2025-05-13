package com.ea_framework.Operators.ChoiceFunctions;

import com.ea_framework.Configurable;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Problems.TSP2DProblem;

import java.util.Map;

public class TSP2DGreedyChoice implements ChoiceFunction, Configurable {

    @Override
    public Object choose(Object current, Object candidate, Object fitnessCurrent, Object fitnessCandidate, int iteration) {
        if (!(current instanceof int[] curr && candidate instanceof int[] cand &&
                fitnessCurrent instanceof Double currFitness && fitnessCandidate instanceof Double candFitness)) {
            throw new IllegalArgumentException("TSP2DGreedyChoice received incompatible types.");
        }

        return candFitness < currFitness ? cand : curr;
    }

    @Override
    public void configure(Map<String, Object> config) {

    }
}
