package com.ea_framework.Registries;

import com.ea_framework.Descriptors.OperatorDescriptor;
import com.ea_framework.OperatorType;

import java.util.*;

public class OperatorRegistry {
    private static final Map<OperatorType, Map<String, OperatorDescriptor>> registry = new EnumMap<>(OperatorType.class);

    static {
        for (OperatorType type : OperatorType.values()) {
            registry.put(type, new HashMap<>());
        }

        // 👇 Register RLS operator
        registry.get(OperatorType.MUTATION).put("RLS",
                new OperatorDescriptor("RLS", "/operator_config/RLS.fxml"));
    }

    public static OperatorDescriptor get(OperatorType type, String name) {
        return registry.get(type).get(name);
    }

    public static Set<String> getNames(OperatorType type) {
        return registry.get(type).keySet();
    }
}