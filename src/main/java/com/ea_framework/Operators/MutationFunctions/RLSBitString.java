package com.ea_framework.Operators.MutationFunctions;

import com.ea_framework.Configs.OperatorConfigs.BitStringMutationConfig;
import com.ea_framework.Configurable;

import java.util.Arrays;
import java.util.Random;

public class RLSBitString implements MutationOperator<boolean[]>, Configurable<BitStringMutationConfig> {

    @Override
    public boolean[] mutate(boolean [] bitString) {
        boolean[] newBitString = Arrays.copyOf(bitString, bitString.length);
        Random rand = new Random();
        int newRand = rand.nextInt(bitString.length);

        newBitString[newRand] = !bitString[newRand];
        return newBitString;
    }


    @Override
    public void configure(BitStringMutationConfig config) {

    }
}
