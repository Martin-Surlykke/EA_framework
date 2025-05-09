package com.ea_framework.Configs;

import java.util.Map;

public interface AlgorithmConfigUI {

    Map<String, Object> getConfigs();

    default void loadConfigs(Map<String, Object> algorithmConfig) {
    }
}
