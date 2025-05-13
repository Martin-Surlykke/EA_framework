package com.ea_framework.Registries;

import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.SearchSpaces.SearchSpace;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Registry {

    public static Set<String> getAllSearchSpaces() {
        return SearchSpaceRegistry.getRegisteredNames();
    }

    public static Set<String> getAllAlgorithms() {
        return AlgorithmRegistry.getAvailableAlgorithms();
    }

    public static Optional<ProblemDescriptor> getProblem(String name) {
        return ProblemRegistry.getProblem(name);
    }

    public static AlgorithmDescriptor getAlgorithm(String name) {
        return AlgorithmRegistry.get(name)
                .orElseThrow(() -> new IllegalArgumentException("Algorithm not found: " + name));
    }

    public static SearchSpace getSearchSpace(String name) {
        return SearchSpaceRegistry.get(name);
    }

    public static List<String> getProblemsForSearchSpace(String searchSpaceKey) {
        return ProblemRegistry.getAvailableProblems().stream()
                .map(ProblemRegistry::getProblem)
                .flatMap(Optional::stream)
                .filter(desc -> searchSpaceKey.equals(desc.getCompatibleKey()))
                .map(ProblemDescriptor::getName)
                .toList();
    }

    public static List<String> getAlgorithmsForProblem(String problemKey) {
        return AlgorithmRegistry.getAll().stream()
                .filter(desc -> problemKey.equals(desc.getCompatibleKey()))
                .map(AlgorithmDescriptor::getName)
                .toList();
    }

    public static AlgorithmDescriptor getAlgorithmDescriptor(String name) {
        return getAlgorithm(name); // alias for clarity
    }
}
