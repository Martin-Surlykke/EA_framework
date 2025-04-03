package com.ea_framework.FitnessFunctions;

public class BitStringLeadingOnes extends BitStringFitness {

    @Override
    public int evaluateBitString(boolean[] bitString) {
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
}
