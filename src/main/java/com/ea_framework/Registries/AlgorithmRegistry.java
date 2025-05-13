package com.ea_framework.Registries;

import com.ea_framework.Descriptors.AlgorithmDescriptor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AlgorithmRegistry {

    private static final Map<String, AlgorithmDescriptor> registry = new HashMap<>();

    public static void register(AlgorithmDescriptor descriptor) {
        registry.put(descriptor.getName(), descriptor);
    }

    public static Optional<AlgorithmDescriptor> get(String name) {
        return Optional.ofNullable(registry.get(name));
    }

    public static Collection<AlgorithmDescriptor> getAll() {
        return registry.values();
    }

    public static Set<String> getAvailableAlgorithms() {
        return registry.keySet();
    }

    public static boolean contains(String name) {
        return registry.containsKey(name);
    }

    public static void clear() {
        registry.clear();
    }
}
