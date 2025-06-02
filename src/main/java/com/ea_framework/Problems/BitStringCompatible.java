package com.ea_framework.Problems;


import com.ea_framework.Operators.FitnessFunctions.Fitness;

public interface BitStringCompatible {
    boolean[] getDefaultPermutation();
    int getLength();
    Fitness getFitnessFunction();
}