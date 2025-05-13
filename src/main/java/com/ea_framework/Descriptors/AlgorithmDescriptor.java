package com.ea_framework.Descriptors;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.OperatorType;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Runners.Runner;
import com.ea_framework.Views.ConfigViews.ConfigView;

import java.util.function.Supplier;

public class AlgorithmDescriptor<
        S,
        P extends Problem<S>,
        A extends Algorithm<S>,
        C extends AlgorithmConfig
        > implements Descriptor {

    private final Class<A> algorithmClass;
    private final String name;
    private final String problemType;
    private final Supplier<ConfigView> configPageSupplier;
    private final Class<? extends AlgorithmConfigUI> controllerClass;
    private final Class<C> configClass;
    private final Runner<S> problemRunner;
    private final OperatorType fitnessOperatorType;
    private final OperatorType mutationOperatorType;
    private final OperatorType choiceOperatorType;

    public AlgorithmDescriptor(
            Class<A> algorithmClass,
            String name,
            String problemType,
            Supplier<ConfigView> configPageSupplier,
            Class<? extends AlgorithmConfigUI> controllerClass,
            Class<C> configClass,
            Runner<S> problemRunner,
            OperatorType fitness,
            OperatorType mutation,
            OperatorType choice
    ) {
        this.algorithmClass = algorithmClass;
        this.name = name;
        this.problemType = problemType;
        this.configPageSupplier = configPageSupplier;
        this.controllerClass = controllerClass;
        this.configClass = configClass;
        this.problemRunner = problemRunner;
        this.fitnessOperatorType = fitness;
        this.mutationOperatorType = mutation;
        this.choiceOperatorType = choice;
    }

    public A create(AlgorithmConfig config) {
        try {
            A algorithm = algorithmClass.getDeclaredConstructor().newInstance();
            algorithm.apply(config);
            return algorithm;
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate algorithm", e);
        }
    }

    public ConfigView getConfigPage() {
        return configPageSupplier.get();
    }

    public Runner<S> getProblemRunner() {
        return problemRunner;
    }

    public Class<? extends AlgorithmConfigUI> getControllerClass() {
        return controllerClass;
    }

    public Class<C> getConfigClass() {
        return configClass;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getCompatibleKey() {
        return problemType;
    }

    public OperatorType getFitnessOperatorType() {
        return fitnessOperatorType;
    }

    public OperatorType getMutationOperatorType() {
        return mutationOperatorType;
    }

    public OperatorType getChoiceOperatorType() {
        return choiceOperatorType;
    }
}