package com.ea_framework.FitnessFunctions;

public class BitStringOneMax implements Fitness<boolean[], Integer> {
    @Override
    public Integer evaluate(boolean[] bitString) {
        int x = 0;
        for (boolean b : bitString) {
            if (b) {
                x += 1;
            }
        }
        return x;
    }
}
