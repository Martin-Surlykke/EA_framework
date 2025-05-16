package com.ea_framework.Operators.MutationFunctions;

import com.ea_framework.Configurable;
import com.ea_framework.Problems.Problem;

import java.util.Arrays;
import java.util.Random;

public class BitStringRLS implements MutationOperator, Configurable {

    @Override
    public Object mutate(Object input) {
        if (!(input instanceof boolean[] bitString)) {
            throw new IllegalArgumentException("Expected boolean[] for RLSBitString");
        }

        boolean[] newBitString = Arrays.copyOf(bitString, bitString.length);
        Random rand = new Random();
        int index = rand.nextInt(bitString.length);

        newBitString[index] = !bitString[index];
        return newBitString;
    }

    @Override
    public void configure(Problem problem) {

    }
}
