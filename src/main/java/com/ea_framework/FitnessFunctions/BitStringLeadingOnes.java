package com.ea_framework.FitnessFunctions;

public class BitStringLeadingOnes implements Fitness<boolean[], Integer> {

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
}
