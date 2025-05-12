package com.ea_framework.Registries;

import com.ea_framework.Descriptors.AlgorithmDescriptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AlgorithmRegistry {
    private static final Map<String, AlgorithmDescriptor<?,?>> registry = new HashMap<>();

    public static void register(AlgorithmDescriptor<?,?> descriptor) {
        registry.put(descriptor.getName(), descriptor);
    }

    public static AlgorithmDescriptor<?,?> get(String name) {
        return registry.get(name);

    }

    public static Collection<AlgorithmDescriptor<?,?>> getAll() {
        return registry.values();
    }

    public static Set<String> getAvailableAlgorithms() {
        return registry.keySet();
    }

    public static AlgorithmDescriptor<?,?> getAlgorithm(String name) {
        AlgorithmDescriptor<?,?> descriptor = registry.get(name);
        if (descriptor != null) {
            return descriptor;
        } else {
            throw new IllegalArgumentException("Algorithm not found: " + name);
        }
    }
}
