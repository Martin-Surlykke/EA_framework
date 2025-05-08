package com.ea_framework.MetaData;

import java.util.HashMap;
import java.util.Map;

public class Registry {

    private static final Map<String, SearchSpaceMetaData> searchSpaces = new HashMap<>();
    private static final Map<String, ProblemMetadata> problems = new HashMap<>();
    private static final Map<String, AlgorithmsMetaData> algorithms = new HashMap<>();


    public static void registerSearchSpace(String name, SearchSpaceMetaData searchSpace) {
        searchSpaces.put(name, searchSpace);
    }

    public static void registerProblem(String name, ProblemMetadata problem) {
        problems.put(name, problem);
    }

    public static void registerAlgorithm(String name, AlgorithmsMetaData algorithm) {
        algorithms.put(name, algorithm);
    }

    public static SearchSpaceMetaData getSearchSpace(String name) {
        return searchSpaces.get(name);
    }

    public static ProblemMetadata getProblem(String name) {
        return problems.get(name);
    }

    public static AlgorithmsMetaData getAlgorithm(String name) {
        return algorithms.get(name);
    }
}
