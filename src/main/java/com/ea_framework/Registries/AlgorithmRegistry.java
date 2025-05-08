package com.ea_framework.Registries;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Problems.Problem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class AlgorithmRegistry {

    private static final Map<String, AlgorithmDescriptor> algorithms = new HashMap<>();

    public static void register(AlgorithmDescriptor descriptor) {
        algorithms.put(descriptor.name(), descriptor);
    }

    public static Problem create(String name) {
        AlgorithmDescriptor descriptor = algorithms.get(name);
        if (descriptor != null) {
            return descriptor.creator().get();
        } else {
            throw new IllegalArgumentException("Algorithm not found: " + name);
        }
    }

    public static Set<String> getAvailableAlgorithms() {
        return algorithms.keySet();
    }
}
