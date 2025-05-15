package com.ea_framework.Operators.FitnessFunctions;

import com.ea_framework.Configs.OperatorConfigs.BitStringFitnessConfig;
import com.ea_framework.Configurable;
import com.ea_framework.Problems.BitStringProblem;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public class BitStringOneMax implements Fitness, Configurable {

    @Override
    public Double evaluate(Object input) {
        if (!(input instanceof boolean[] bitString)) {
            throw new IllegalArgumentException("Expected boolean[] in BitStringOneMax");
        }

        int count = 0;
        for (boolean b : bitString) {
            if (b) count++;
        }
        return (double) count;
    }

    @Override
    public void configure(Problem problem) {

    }
}
