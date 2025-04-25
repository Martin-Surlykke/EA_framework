package com.ea_framework;

import com.ea_framework.Candidates.Candidate;
import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.Mutation.MutationOperator;
import com.ea_framework.StartAlgorithms.StartAlgorithm;

public class RunConfiguration {
    public Candidate candidate;
    public Fitness<?, ?> fitness;
    public MutationOperator<?> mutationOperator;
    public ChoiceFunction<?, ?> choiceFunction;
    public StartAlgorithm <?, ?> startAlgorithm;

}
