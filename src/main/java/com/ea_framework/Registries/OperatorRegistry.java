package com.ea_framework.Registries;

import com.ea_framework.Descriptors.OperatorDescriptor;
import com.ea_framework.OperatorType;

import java.util.*;
import java.util.stream.Collectors;

public class OperatorRegistry {
    private static final Map<String, OperatorDescriptor> registry = new HashMap<>();

    public static void register(String name, OperatorDescriptor descriptor) {
        registry.put(name, descriptor);
    }

    public static OperatorDescriptor get(String name) {
        return registry.get(name);
    }

    public static Set<String> getAvailableOperatorsByType(OperatorType type) {
        Set<String> names = new HashSet<>();
        for (Map.Entry<String, OperatorDescriptor> entry : registry.entrySet()) {
            if (entry.getValue().getType() == type) {
                names.add(entry.getKey());
            }
        }
        return names;
    }

    public static Map<String, OperatorDescriptor> getRegistryByType(OperatorType type) {
        return registry.entrySet().stream()
                .filter(e -> e.getValue().getType() == type)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}