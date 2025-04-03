package com.ea_framework.FitnessFunctions;

public class BitStringOneMax extends BitStringFitness {
    @Override
    public int evaluateBitString(boolean[] bitString) {
        int x = 0;
        for (boolean b : bitString) {
            if (b) {
                x += 1;
            }
        }
        return x;
    }
}
