package com.ea_framework.Registries;

import com.ea_framework.Configurable;
import com.ea_framework.Descriptors.OperatorDescriptor;
import com.ea_framework.OperatorType;

import java.util.*;
import java.util.stream.Collectors;

public class OperatorRegistry {

    private static final Map<String, OperatorDescriptor<?, ?>> registry = new HashMap<>();

    public static <T extends Configurable<C>, C> void register(String name, OperatorDescriptor<T, C> descriptor) {
        registry.put(name, descriptor);
    }

    public static <T extends Configurable<C>, C> OperatorDescriptor<T, C> get(String name, Class<T> opClass, Class<C> configClass) {
        OperatorDescriptor<?, ?> raw = registry.get(name);
        if (raw == null) {
            throw new IllegalArgumentException("Operator not found: " + name);
        }

        @SuppressWarnings("unchecked")
        OperatorDescriptor<T, C> typed = (OperatorDescriptor<T, C>) raw;
        return typed;
    }

    // === Legacy or filtered access ===

    public static Set<String> getAvailableOperatorsByType(OperatorType type) {
        return registry.entrySet().stream()
                .filter(e -> e.getValue().getType() == type)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static Map<String, OperatorDescriptor<?, ?>> getRegistryByType(OperatorType type) {
        return registry.entrySet().stream()
                .filter(e -> e.getValue().getType() == type)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Collection<OperatorDescriptor<?, ?>> getAll() {
        return registry.values();
    }

    public static boolean contains(String name) {
        return registry.containsKey(name);
    }
}
