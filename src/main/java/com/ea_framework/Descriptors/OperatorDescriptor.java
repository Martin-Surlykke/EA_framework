package com.ea_framework.Descriptors;

import com.ea_framework.UIs.GenericOperatorUI;

public class OperatorDescriptor {
    private final String name;
    private final GenericOperatorUI configUI;

    public OperatorDescriptor(String name, GenericOperatorUI configUI) {
        this.name = name;
        this.configUI = configUI;
    }

    public String getName() {
        return name;
    }

    public GenericOperatorUI getConfigUI() {
        return configUI;
    }
}