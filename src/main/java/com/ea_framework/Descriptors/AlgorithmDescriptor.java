package com.ea_framework.Descriptors;

import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.OperatorType;
import com.ea_framework.Views.ConfigViews.ConfigView;

import java.util.function.Function;
import java.util.function.Supplier;

public class AlgorithmDescriptor<T, C> implements Descriptor {
    private final String name;
    private final String problemType;
    private final Function<C, T> algorithm;
    private final Supplier<ConfigView> configPageSupplier;
    private final Class<? extends AlgorithmConfigUI> controllerClass;
    private final Class<C> configClass;

    private final OperatorType fitnessOperatorType;
    private final OperatorType mutationOperatorType;
    private final OperatorType choiceOperatorType;

    public AlgorithmDescriptor(String name, String problemType,
                               Function<C, T> algorithm,
                               Supplier<ConfigView> configPageSupplier,
                               Class<? extends AlgorithmConfigUI> controllerClass,
                               Class<C> configClass,
                               OperatorType fitnessOperatorType,
                               OperatorType mutationOperatorType,
                               OperatorType choiceOperatorType) {
        this.name = name;
        this.problemType = problemType;
        this.algorithm = algorithm;
        this.configPageSupplier = configPageSupplier;
        this.controllerClass = controllerClass;
        this.configClass = configClass;
        this.fitnessOperatorType = fitnessOperatorType;
        this.mutationOperatorType = mutationOperatorType;
        this.choiceOperatorType = choiceOperatorType;
    }

    public T create(C config) {

        return algorithm.apply(config);
    }

    public ConfigView getConfigPage() {

        return configPageSupplier.get();
    }

    public String getName() {

        return name;
    }

    @Override
    public String getCompatibleKey() {

        return problemType;
    }

    public Class<? extends AlgorithmConfigUI> getControllerClass() {

        return controllerClass;
    }

    public Class<C> getConfigClass() {

        return configClass;
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

    public Function<C, T> getAlgorithm() {
        return algorithm;
    }

    public T createAlgorithm(Object config) {
        return algorithm.apply((C) config);
    }
}