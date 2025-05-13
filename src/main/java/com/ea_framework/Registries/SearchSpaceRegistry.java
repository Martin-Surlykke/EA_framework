package com.ea_framework.Registries;

import com.ea_framework.SearchSpaces.SearchSpace;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class SearchSpaceRegistry {

    private static final Map<String, TypedSearchSpace<?>> registry = new HashMap<>();

    public static <S> void register(String name, Supplier<SearchSpace<S>> supplier, Class<S> solutionType) {
        registry.put(name, new TypedSearchSpace<>(supplier, solutionType));
    }

    public static <S> SearchSpace<S> get(String name, Class<S> solutionType) {
        TypedSearchSpace<?> entry = registry.get(name);
        if (entry == null) {
            throw new IllegalArgumentException("Search space not found: " + name);
        }

        if (!entry.solutionType.equals(solutionType)) {
            throw new IllegalArgumentException("Type mismatch for search space: expected " + solutionType.getSimpleName());
        }

        @SuppressWarnings("unchecked")
        TypedSearchSpace<S> typed = (TypedSearchSpace<S>) entry;
        return typed.supplier.get();
    }

    public static Set<String> getRegisteredNames() {
        return registry.keySet();
    }

    private static final class TypedSearchSpace<S> {
        final Supplier<SearchSpace<S>> supplier;
        final Class<S> solutionType;

        TypedSearchSpace(Supplier<SearchSpace<S>> supplier, Class<S> solutionType) {
            this.supplier = supplier;
            this.solutionType = solutionType;
        }

    }
}