package com.ea_framework.Descriptors;


import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Views.ConfigViews.ConfigView;

import java.util.function.Function;
import java.util.function.Supplier;

public class AlgorithmDescriptor<T, C extends AlgorithmConfig> {
    private final String name;
    private final String problemType;
    private final Function<C, T> constructor;
    private final Supplier<ConfigView> configPageSupplier;

    public AlgorithmDescriptor(String name, String problemType,
                                           Function<C, T> constructor,
                                           Supplier<ConfigView> configPageSupplier) {
        this.name = name;
        this.problemType = problemType;
        this.constructor = constructor;
        this.configPageSupplier = configPageSupplier;
    }

    public T create(C config) {
        return constructor.apply(config);
    }

    public ConfigView getConfigPage() {
        return configPageSupplier.get();
    }

    public String getName() {
        return name;
    }

    public String getProblemType() {
        return problemType;
    }
}