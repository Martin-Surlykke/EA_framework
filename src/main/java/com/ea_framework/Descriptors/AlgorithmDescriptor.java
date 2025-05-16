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

    public AlgorithmDescriptor(
            Class<? extends Algorithm> algorithmClass,
            String name,
            String problemType,
            Supplier<ConfigView> configPageSupplier
    ) {
        this.algorithmClass = algorithmClass;
        this.name = name;
        this.problemType = problemType;
        this.configPageSupplier = configPageSupplier;
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

    public String getName() {
        return name;
    }

    @Override
    public String getCompatibleKey() {
        return problemType;
    }
}
