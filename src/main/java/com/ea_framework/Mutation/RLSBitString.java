package com.ea_framework.Mutation;

import java.util.Arrays;
import java.util.Random;

public class RLSBitString implements MutationOperator<boolean[]> {

    @Override
    public boolean[] mutate(boolean [] bitString) {
        boolean[] newBitString = Arrays.copyOf(bitString, bitString.length);
        Random rand = new Random();
        int newRand = rand.nextInt(bitString.length);

        newBitString[newRand] = !bitString[newRand];
        return newBitString;
    }


}
