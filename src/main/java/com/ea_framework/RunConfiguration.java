package com.ea_framework;

import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.MutationFunctions.MutationOperator;

public class RunConfiguration {

    public Fitness<?, ?> fitness;
    public MutationOperator<?> mutationOperator;
    public ChoiceFunction<?, ?> choiceFunction;

}
