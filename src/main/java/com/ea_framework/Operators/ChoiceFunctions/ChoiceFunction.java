package com.ea_framework.Operators.ChoiceFunctions;

public interface ChoiceFunction<Permutation, Value> {
    Permutation choose(Permutation current, Permutation candidate,
                  Value fitnessCurrent, Value fitnessCandidate,
                  int iteration);
}