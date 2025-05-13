package com.ea_framework.Operators.MutationFunctions;

import com.ea_framework.Configs.OperatorConfigs.BitStringMutationConfig;
import com.ea_framework.Configurable;

import java.util.Random;

public class One_One_EA_BitString implements MutationOperator<boolean[]>, Configurable<BitStringMutationConfig> {
    @Override
    public boolean [] mutate(boolean[] currentSolution) {
        int n = currentSolution.length;
        double probability = 1.0/n;

        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            if(rand.nextDouble() < probability) {
               currentSolution[i] = !currentSolution[i];
            }

        }
        return currentSolution;
    }

    @Override
    public void configure(BitStringMutationConfig config) {

    }
}
