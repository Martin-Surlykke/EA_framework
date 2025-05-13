package com.ea_framework.Configs;

import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.MutationFunctions.MutationOperator;

import java.util.Map;

public class TSP2DConfig implements AlgorithmConfig {

    private Fitness<int[], Double> fitness;
    private MutationOperator<int[]> mutation;
    private ChoiceFunction<int[], Double> choice;

    public TSP2DConfig() {
    }


    @Override
    public void populate(Map<String, Object> values) {
        this.fitness = (Fitness<int[], Double>) values.get("fitness");
        this.mutation = (MutationOperator<int[]>) values.get("mutation");
        this.choice = (ChoiceFunction<int[], Double>) values.get("choice");

        if (fitness == null || mutation == null || choice == null) {
            throw new IllegalArgumentException("Missing one or more algorithm components");
        }
    }

    public Fitness<int[], Double> fitness() {
        return fitness;
    }

    public MutationOperator<int[]> mutation() {
        return mutation;
    }

    public ChoiceFunction<int[], Double> choice() {
        return choice;
    }
}
