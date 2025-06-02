package com.ea_framework.Operators.FitnessFunctions;

import com.ea_framework.Configurable;
import com.ea_framework.Problems.Problem;

public class BitStringOneMax implements Fitness, Configurable {

    @Override
    public Integer evaluate(Object input) {
        if (!(input instanceof boolean[] bitString)) {
            throw new IllegalArgumentException("Expected boolean[] in BitStringOneMax");
        }

        int count = 0;
        for (boolean b : bitString) {
            if (b) count++;
        }
        return count;
    }

    @Override
    public void configure(Problem problem) {

    }
}
