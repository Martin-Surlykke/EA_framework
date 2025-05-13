package com.ea_framework.Configs;

import com.ea_framework.Configurable;
import com.ea_framework.Operators.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Operators.MutationFunctions.MutationOperator;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public class TSP2DConfig implements AlgorithmConfig {

    private Fitness fitness;
    private MutationOperator mutation;
    private ChoiceFunction choice;

    public TSP2DConfig() {}

    @Override
    public void populate(Map<String, Object> raw, Problem problem) {
        Object fitnessObj = raw.get("fitness");
        Object mutationObj = raw.get("mutation");
        Object choiceObj = raw.get("choice");

        if (fitnessObj instanceof Fitness f) {
            this.fitness = f;
            if (raw.get("fitnessConfig") instanceof Map<?, ?> config && f instanceof Configurable configurable) {
                configurable.configure(cast(config));
            }
        }

        if (mutationObj instanceof MutationOperator m) {
            this.mutation = m;
            if (raw.get("mutationConfig") instanceof Map<?, ?> config && m instanceof Configurable configurable) {
                configurable.configure(cast(config));
            }
        }

        if (choiceObj instanceof ChoiceFunction c) {
            this.choice = c;
            if (raw.get("choiceConfig") instanceof Map<?, ?> config && c instanceof Configurable configurable) {
                configurable.configure(cast(config));
            }
        }
    }

    private static Map<String, Object> cast(Map<?, ?> map) {
        return (Map<String, Object>) map;
    }

    public Fitness fitness() {
        return fitness;
    }

    public MutationOperator mutation() {
        return mutation;
    }

    public ChoiceFunction choice() {
        return choice;
    }
}
