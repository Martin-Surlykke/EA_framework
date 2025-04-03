package com.ea_framework.Algorithms;
import com.ea_framework.Mutation.BitStringMutationOperator;

import java.util.Random;

public class one_one_EA_bitString extends BitStringMutationOperator {
    @Override
    public void mutate(boolean[] currentSolution) {
        int n = currentSolution.length;
        double probability = 1.0/n;

        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            if(rand.nextDouble() < probability) {
               currentSolution[i] = !currentSolution[i];
            }

        }
    }

}
