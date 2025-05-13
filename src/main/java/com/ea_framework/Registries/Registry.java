package com.ea_framework.Registries;

import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.SearchSpaces.SearchSpace;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.AlgorithmConfig;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class Registry {

    public static Set<String> getAllSearchSpaces() {
        return SearchSpaceRegistry.getRegisteredNames();
    }

    public static Set<String> getAllProblems() {
        return ProblemRegistry.getAvailableProblems();
    }

    public static Set<String> getAllAlgorithms() {
        return AlgorithmRegistry.getAvailableAlgorithms();
    }

    public static <S, P extends Problem<S>> Optional<ProblemDescriptor<S, P>> getProblem(String name, Class<S> s, Class<P> p) {
        return ProblemRegistry.getProblem(name);
    }

    public static <S, P extends Problem<S>, A extends Algorithm<S>, C extends AlgorithmConfig>
    AlgorithmDescriptor<S, P, A, C> getAlgorithm(String name, Class<S> s, Class<P> p, Class<A> a, Class<C> c) {
        return AlgorithmRegistry.get(name, s, p, a, c);
    }

    public static <S> SearchSpace<S> createSearchSpace(String name, Class<S> s) {
        return SearchSpaceRegistry.get(name, s);
    }

    public static List<String> getProblemsForSearchSpace(String searchSpace) {
        return ProblemRegistry.getAvailableProblems().stream()
                .flatMap(name -> ProblemRegistry
                        .get(name, Object.class, Problem.class)
                        .filter(desc ->
                                searchSpace.equals(desc.getCompatibleKey())).stream().map(ProblemDescriptor::getName)
                )
                .toList();
    }

    public static List<String> getAlgorithmsForProblem(String problemName) {
        return AlgorithmRegistry.getAll().stream()
                .filter(descriptor -> problemName.equals(descriptor.getCompatibleKey()))
                .map(AlgorithmDescriptor::getName)
                .toList();
    }

    public static SearchSpace getSearchSpace(String searchSpace) {
        return SearchSpaceRegistry.get(searchSpace, Object.class);
    }

    public static AlgorithmDescriptor getAlgorithmDescriptor(String algorithmName) {
        return AlgorithmRegistry.getAll().stream()
                .filter(descriptor -> algorithmName.equals(descriptor.getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Algorithm not found: " + algorithmName));
    }


}
