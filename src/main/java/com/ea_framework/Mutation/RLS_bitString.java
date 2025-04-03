package com.ea_framework.Mutation;

import java.util.Random;

public class RLS_bitString extends BitStringMutationOperator {

    @Override
    public void mutate(boolean [] bitString) {
        Random rand = new Random();
        int newRand = rand.nextInt(bitString.length);

        bitString[newRand] = !bitString[newRand];
    }


}
