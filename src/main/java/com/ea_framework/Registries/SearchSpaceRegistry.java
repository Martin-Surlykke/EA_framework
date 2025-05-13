package com.ea_framework.Registries;

import com.ea_framework.SearchSpaces.SearchSpace;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class SearchSpaceRegistry {

    private static final Map<String, Supplier<SearchSpace>> registry = new HashMap<>();

    public static void register(String name, Supplier<SearchSpace> supplier) {
        registry.put(name, supplier);
    }

    public static SearchSpace get(String name) {
        Supplier<SearchSpace> supplier = registry.get(name);
        if (supplier == null) {
            throw new IllegalArgumentException("Search space not found: " + name);
        }
        return supplier.get();
    }

    public static Set<String> getRegisteredNames() {
        return registry.keySet();
    }

    public static void clear() {
        registry.clear();
    }
}
