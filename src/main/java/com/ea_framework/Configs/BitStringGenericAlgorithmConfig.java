package com.ea_framework.Configs;

import com.ea_framework.Configurable;
import com.ea_framework.HasFitness;
import com.ea_framework.Operators.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Operators.MutationFunctions.MutationOperator;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public class BitStringGenericAlgorithmConfig implements AlgorithmConfig {

    // This class is designed to configure a generic algorithm for bit strings,
    private MutationOperator mutation;
    private ChoiceFunction choice;

    private Fitness fitness;

    // Populate the configuration with the problem's operators and fitness function
    @Override
    public void populate(Map<String, Object> raw, Problem problem) {
        this.mutation = (MutationOperator) raw.get("mutation");
        this.choice = (ChoiceFunction) raw.get("choice");

        if (mutation instanceof Configurable m) m.configure(problem);
        if (choice instanceof Configurable c) c.configure(problem);


        // statement for pulling fitness direcly from problem class
        System.out.println("Populating config with problem: " + problem.getClass());
        if (problem instanceof HasFitness hasFitness) {
            this.fitness = hasFitness.getFitnessFunction();
            System.out.println("Fitness set to: " + fitness.getClass().getSimpleName());
        } else {
            throw new IllegalArgumentException("Problem must implement HasFitness to provide fitness function");
        }
    }

    // getters for operators

    public MutationOperator mutation() { return mutation; }
    public ChoiceFunction choice() { return choice; }

    public Fitness fitness() {
        return fitness;
    }
}
