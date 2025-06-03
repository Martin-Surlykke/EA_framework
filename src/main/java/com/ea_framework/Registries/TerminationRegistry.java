package com.ea_framework.Registries;

import com.ea_framework.Descriptors.TerminationDescriptor;

import java.util.*;

public class TerminationRegistry {
    private static final Map<String, TerminationDescriptor> terminationMap = new LinkedHashMap<>();

    public static void register(String name, TerminationDescriptor descriptor) {
        terminationMap.put(name, descriptor);
    }

    public static Optional<TerminationDescriptor> get(String name) {
        return Optional.ofNullable(terminationMap.get(name));
    }

    public static List<String> getAvailableNames() {
        return new ArrayList<>(terminationMap.keySet());
    }

    public static List<TerminationDescriptor> getAll() {
        return new ArrayList<>(terminationMap.values());
    }

    public static Set<String> getRegisteredNames() {
        return terminationMap.keySet();
    }
}
