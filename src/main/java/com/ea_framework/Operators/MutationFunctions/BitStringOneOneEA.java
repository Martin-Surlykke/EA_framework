package com.ea_framework.Operators.MutationFunctions;

import com.ea_framework.Configurable;
import com.ea_framework.Problems.Problem;

import java.util.Random;

public class BitStringOneOneEA implements MutationOperator, Configurable {

    private int x = 1;  // default value

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public Object mutate(Object currentSolution) {
        if (!(currentSolution instanceof boolean[] bits)) {
            throw new IllegalArgumentException("Expected boolean[] for One_One_EA_BitString");
        }

        int n = bits.length;
        double probability = (double) x / n;
        Random rand = new Random();

        boolean[] mutated = bits.clone();

        for (int i = 0; i < n; i++) {
            if (rand.nextDouble() < probability) {
                mutated[i] = !mutated[i];
            }
        }

        return mutated;
    }

    @Override
    public void configure(Problem problem) {
        // No-op for now
    }
}

