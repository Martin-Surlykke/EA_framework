package com.ea_framework.FitnessFunctions;

public abstract class BitStringFitness implements Fitness<boolean [], Integer> {

    @Override
    public Integer evaluate(boolean[] solution) {
        return evaluateBitString(solution);
    }

    public abstract int evaluateBitString(boolean[] solution);
}
