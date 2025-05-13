package com.ea_framework.Operators.ChoiceFunctions;

public interface ChoiceFunction {
    Object choose(Object current,
                  Object candidate,
                  Object fitnessCurrent,
                  Object fitnessCandidate,
                  int iteration);
}
