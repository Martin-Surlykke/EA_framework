package com.ea_framework.Registries;

import com.ea_framework.SearchSpaces.SearchSpace;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Supplier;

public class SearchSpaceRegistry {

    private static final HashMap<String, Supplier<SearchSpace<?>>> searchSpaces = new HashMap<>();

    public static void register(String name, Supplier<SearchSpace<?>> searchSpace) {
        searchSpaces.put(name, searchSpace);
    }

    public static SearchSpace<?> createSearchSpace(String name) {
        Supplier<SearchSpace<?>> supplier = searchSpaces.get(name);
        if (supplier != null) {
            return supplier.get();
        } else {
            throw new IllegalArgumentException("Search space not found: " + name);
        }
    }

    public static Set<String> getRegisteredSearchSpaces() {
        return searchSpaces.keySet();
    }



}
