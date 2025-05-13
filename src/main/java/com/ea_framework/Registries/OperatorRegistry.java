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

    public static Optional<OperatorDescriptor> get(String name) {
        return Optional.ofNullable(registry.get(name));
    }

    public static Set<String> getAvailableOperatorsByType(OperatorType type) {
        return registry.entrySet().stream()
                .filter(e -> e.getValue().getType() == type)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static Map<String, OperatorDescriptor> getRegistryByType(OperatorType type) {
        return registry.entrySet().stream()
                .filter(e -> e.getValue().getType() == type)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Collection<OperatorDescriptor> getAll() {
        return registry.values();
    }

    public static boolean contains(String name) {
        return registry.containsKey(name);
    }

    public static void clear() {
        registry.clear();
    }
}
