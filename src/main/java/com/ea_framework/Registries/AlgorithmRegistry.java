package com.ea_framework.Registries;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Problems.Problem;

import java.util.*;

public class AlgorithmRegistry {

    private static final Map<String, AlgorithmDescriptor<?, ?, ?, ?>> registry = new HashMap<>();

    // Generic registration
    public static <S, P extends Problem<S>, A extends Algorithm<S>, C extends AlgorithmConfig>
    void register(AlgorithmDescriptor<S, P, A, C> descriptor) {
        registry.put(descriptor.getName(), descriptor);
    }

    // Typed access: must provide expected types explicitly
    public static <S, P extends Problem<S>, A extends Algorithm<S>, C extends AlgorithmConfig>
    AlgorithmDescriptor<S, P, A, C> get(String name, Class<S> s, Class<P> p, Class<A> a, Class<C> c) {
        AlgorithmDescriptor<?, ?, ?, ?> raw = registry.get(name);
        if (raw == null) {
            throw new IllegalArgumentException("Algorithm not found: " + name);
        }

        // Single centralized unchecked cast
        @SuppressWarnings("unchecked")
        AlgorithmDescriptor<S, P, A, C> descriptor = (AlgorithmDescriptor<S, P, A, C>) raw;
        return descriptor;
    }

    public static Collection<AlgorithmDescriptor<?, ?, ?, ?>> getAll() {
        return registry.values();
    }

    public static Set<String> getAvailableAlgorithms() {
        return registry.keySet();
    }

    public static boolean contains(String name) {
        return registry.containsKey(name);
    }
}
