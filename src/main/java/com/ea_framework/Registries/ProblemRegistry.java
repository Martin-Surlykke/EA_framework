package com.ea_framework.Registries;

import com.ea_framework.Descriptors.ProblemDescriptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class ProblemRegistry {

    private static final Map<String, ProblemDescriptor> registry = new HashMap<>();

    public static void register(String name, ProblemDescriptor descriptor) {
        registry.put(name, descriptor);
    }

    public static Optional<ProblemDescriptor> getProblem(String name) {
        return Optional.ofNullable(registry.get(name));
    }

    public static boolean contains(String name) {
        return registry.containsKey(name);
    }

    public static void clear() {
        registry.clear();
    }

    public static Set<String> getAvailableProblems() {
        return registry.keySet();
    }

}
