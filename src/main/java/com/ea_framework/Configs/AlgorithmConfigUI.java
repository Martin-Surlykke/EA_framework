package com.ea_framework.Configs;

import com.ea_framework.OperatorType;

import java.util.Map;

public interface AlgorithmConfigUI {

    void bindTo(Object config);
    Map<String, Object> getConfigs();

    default OperatorType[] getOperatorTypes() {
        return new OperatorType[0];
    }

    default void loadConfigs(Map<String, Object> algorithmConfig) {
    }

}
