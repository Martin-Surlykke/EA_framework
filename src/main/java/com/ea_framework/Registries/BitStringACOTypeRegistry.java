package com.ea_framework.Registries;

import com.ea_framework.ACOTypes.BitStringACOType;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BitStringACOTypeRegistry {

    private static final Map<String, BitStringACOType> registry = new HashMap<>();

    public static void register(String name, BitStringACOType type) {
        registry.put(name, type);
    }

    public static BitStringACOType get(String name) {
        return registry.get(name);
    }

    public static Set<String> getAllNames() {
        return registry.keySet();
    }
}