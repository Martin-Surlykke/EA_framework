package com.ea_framework.Descriptors;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.OperatorType;
import com.ea_framework.Views.ConfigViews.ConfigView;

import java.util.function.Supplier;

public class AlgorithmDescriptor implements Descriptor {

    private final Class<? extends Algorithm> algorithmClass;
    private final String name;
    private final String problemType;
    private final Supplier<ConfigView> configPageSupplier;
    private final Class<? extends AlgorithmConfigUI> controllerClass;
    private final Class<? extends AlgorithmConfig> configClass;
    private final OperatorType fitnessOperatorType;
    private final OperatorType mutationOperatorType;
    private final OperatorType choiceOperatorType;

    public AlgorithmDescriptor(
            Class<? extends Algorithm> algorithmClass,
            String name,
            String problemType,
            Supplier<ConfigView> configPageSupplier,
            Class<? extends AlgorithmConfigUI> controllerClass,
            Class<? extends AlgorithmConfig> configClass,
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
        this.fitnessOperatorType = fitness;
        this.mutationOperatorType = mutation;
        this.choiceOperatorType = choice;
    }

    public Algorithm create(AlgorithmConfig config) {
        try {
            Algorithm algorithm = algorithmClass.getDeclaredConstructor().newInstance();
            algorithm.apply(config);
            return algorithm;
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate algorithm: " + algorithmClass.getSimpleName(), e);
        }
    }

    public ConfigView getConfigPage() {
        return configPageSupplier.get();
    }

    public Class<? extends AlgorithmConfig> getConfigClass() {
        return configClass;
    }

    public Class<? extends AlgorithmConfigUI> getControllerClass() {
        return controllerClass;
    }

    public Class<? extends Algorithm> getAlgorithmClass() {
        return algorithmClass;
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
