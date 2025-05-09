package com.ea_framework.Configs;


import java.util.HashMap;
import java.util.Map;

public class BatchConfig {
    private String searchSpace;
    private String problem;
    private String algorithm;
    private int repeats;

    private final Map<String, Object> AlgorithmConfig = new HashMap<>();
    private final Map<String, Object> metaConfig = new HashMap<>();

    public BatchConfig() {}

    public String getSearchSpace() {
        return searchSpace;
    }

    public void setSearchSpace(String searchSpace) {
        this.searchSpace = searchSpace;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public Map<String, Object> getAlgorithmConfig() {
        return AlgorithmConfig;
    }

    public Map<String, Object> getMetaConfig() {
        return metaConfig;
    }

    public String toSummaryString() {
        return "%s + %s [%s], repeats=%d"
                .formatted(algorithm, problem, searchSpace, repeats);
    }
}
