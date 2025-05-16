package com.ea_framework.Configs;

import com.ea_framework.Configurable;
import com.ea_framework.Operators.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Operators.MutationFunctions.MutationOperator;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public class BitStringGenericAlgorithmConfig implements AlgorithmConfig {

    private Fitness fitness;
    private MutationOperator mutation;
    private ChoiceFunction choice;

    @Override
    public void populate(Map<String, Object> raw, Problem problem) {
        this.fitness = (Fitness) raw.get("fitness");
        this.mutation = (MutationOperator) raw.get("mutation");
        this.choice = (ChoiceFunction) raw.get("choice");

        if (fitness instanceof Configurable f) f.configure(problem);
        if (mutation instanceof Configurable m) m.configure(problem);
        if (choice instanceof Configurable c) c.configure(problem);
    }

    public Fitness fitness() { return fitness; }
    public MutationOperator mutation() { return mutation; }
    public ChoiceFunction choice() { return choice; }
}
