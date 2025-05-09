package com.ea_framework.Registries;

import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.SearchSpaces.SearchSpace;

import java.util.Set;

public class Registry {

    public static Set<String> getAllSearchSpaces() {
        return SearchSpaceRegistry.getRegisteredSearchSpaces();
    }

    public static Set<String> getAllProblems() {
        return ProblemRegistry.getAvailableProblems();
    }

    public static Set<String> getAllAlgorithms() {
        return AlgorithmRegistry.getAvailableAlgorithms();
    }

    public static ProblemDescriptor getProblem(String name) {
        return ProblemRegistry.getDescriptor(name);
    }

    public static AlgorithmDescriptor<?, ?> getAlgorithm(String name) {
        return AlgorithmRegistry.get(name);
    }

    public static SearchSpace<?> createSearchSpace(String name) {
        return SearchSpaceRegistry.createSearchSpace(name);
    }
}