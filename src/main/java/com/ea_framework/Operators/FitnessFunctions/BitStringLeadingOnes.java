package com.ea_framework.Operators.FitnessFunctions;

import com.ea_framework.Configs.OperatorConfigs.BitStringFitnessConfig;
import com.ea_framework.Configurable;

public class BitStringLeadingOnes implements Fitness<boolean[], Integer>, Configurable<BitStringFitnessConfig> {

    @Override
    public Integer evaluate(boolean[] bitString) {
        int x = 0;
        for (boolean b : bitString) {
            if (!b) {
                break;
            } else {
                x += 1;
            }
        }
        return x;
    }

    @Override
    public void configure(BitStringFitnessConfig config) {

    }
}
