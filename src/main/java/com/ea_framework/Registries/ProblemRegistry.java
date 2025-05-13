package com.ea_framework.Registries;

import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Problems.Problem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProblemRegistry {

    private static final Map<String, ProblemDescriptor<?, ?>> registry = new HashMap<>();

    public static <S, P extends Problem<S>> void register(
            String name,
            ProblemDescriptor<S, P> descriptor
    ) {
        registry.put(name, descriptor);
    }

        public static <S, P extends Problem<S>> Optional<ProblemDescriptor<S, P>> getProblem(
                String name
        ) {
            ProblemDescriptor<?, ?> raw = registry.get(name);

            if (raw == null) return Optional.empty();

            return Optional.of((ProblemDescriptor<S, P>) raw);

        }
}

