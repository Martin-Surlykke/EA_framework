package com.ea_framework.Registries;

import com.ea_framework.ACOTypes.ACOType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ACOTypeRegistry {

    private static final Map<String, ACOType> registry = new HashMap<>();

    public static void register(String name, ACOType type) {
        registry.put(name, type);
    }

    public static ACOType get(String name) {
        return registry.get(name);
    }

    public static Set<String> getAllNames() {
        return registry.keySet();
    }
}