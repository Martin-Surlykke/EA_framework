package com.ea_framework.Registries;

import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Problems.Problem;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ProblemRegistry {

    private static final Map<String, ProblemDescriptor> problems = new HashMap<>();

    public static void register(ProblemDescriptor descriptor) {
        problems.put(descriptor.name(), descriptor);
    }

    public static Problem create(String name) {
        ProblemDescriptor descriptor = problems.get(name);
        if (descriptor != null) {
            return descriptor.creator().get();
        } else {
            throw new IllegalArgumentException("Problem not found: " + name);
        }
    }

    public static Set<String> getAvailableProblems() {
        return problems.keySet();
    }
}
