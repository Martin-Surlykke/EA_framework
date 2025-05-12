package com.ea_framework.Registries;

import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Problems.Problem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ProblemRegistry {

    private static final Map<String, ProblemDescriptor> problems = new HashMap<>();

    public static void register(ProblemDescriptor descriptor) {
        problems.put(descriptor.getName(), descriptor);
    }

    public static ProblemDescriptor getDescriptor (String name) {
        return problems.get(name);
    }

    public static Problem create(String name, InputStream stream) throws IOException {
        ProblemDescriptor descriptor = problems.get(name);
        if (descriptor == null) {
            throw new IllegalArgumentException("Problem not found: " + name);
        } else {
            return descriptor.getLoader().load(stream);
        }

    }

    public static Set<String> getAvailableProblems() {
        return problems.keySet();
    }
}
