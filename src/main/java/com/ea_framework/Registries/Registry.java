package com.ea_framework.Registries;

import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.SearchSpaces.SearchSpace;

import java.util.List;
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

    public static List<String> getProblemsForSearchSpace(String searchSpace) {
        return ProblemRegistry.getAvailableProblems().stream()
                .map(ProblemRegistry::getDescriptor)
                .filter(desc -> desc != null && searchSpace.equals(desc.getCompatibleKey()))
                .map(ProblemDescriptor::getName)
                .toList();
    }

    public static List<String> getAlgorithmsForProblem(String problemName) {
        return AlgorithmRegistry.getAll().stream()
                .filter(descriptor -> problemName.equals(descriptor.getCompatibleKey()))
                .map(AlgorithmDescriptor::getName)
                .toList();
    }
}