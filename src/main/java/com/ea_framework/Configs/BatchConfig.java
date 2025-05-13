package com.ea_framework.Configs;

import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Problems.Problem;
import com.ea_framework.SearchSpaces.SearchSpace;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BatchConfig<
        S,
        P extends Problem<S>,
        A extends com.ea_framework.Algorithms.Algorithm<S>,
        C extends AlgorithmConfig
        > {

    private String streamName;
    private InputStream inputStream;

    private String searchSpaceName;
    private SearchSpace<S> searchSpace;

    private String problemName;
    private ProblemDescriptor<S, P> problemDescriptor;
    private P problem;

    private String algorithmName;
    private AlgorithmDescriptor<S, P, A, C> algorithmDescriptor;
    private C algorithmConfig;

    private final Map<String, String> terminationConfigs = new HashMap<>();
    private final Map<String, String> metaConfigs = new HashMap<>();

    private int repeats;
    private int termination;

    // === Getters and Setters ===

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getSearchSpaceName() {
        return searchSpaceName;
    }

    public void setSearchSpaceName(String searchSpaceName) {
        this.searchSpaceName = searchSpaceName;
    }

    public SearchSpace<S> getSearchSpace() {
        return searchSpace;
    }

    public void setSearchSpace(SearchSpace<S> searchSpace) {
        this.searchSpace = searchSpace;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public AlgorithmDescriptor<S, P, A, C> getAlgorithmDescriptor() {
        return algorithmDescriptor;
    }

    public void setAlgorithmDescriptor(AlgorithmDescriptor<S, P, A, C> descriptor) {
        this.algorithmDescriptor = descriptor;
    }

    public ProblemDescriptor<S, P> getProblemDescriptor() {
        return problemDescriptor;
    }

    public void setProblemDescriptor(ProblemDescriptor<S, P> problemDescriptor) {
        this.problemDescriptor = problemDescriptor;
    }

    public C getAlgorithmConfig() {
        return algorithmConfig;
    }

    public void setAlgorithmConfig(C config) {
        this.algorithmConfig = config;
    }

    public P getProblem() throws IOException {
        if (problem == null) {
            problem = getProblemFromDescriptor();
        }
        return problem;
    }

    public P getProblemFromDescriptor() throws IOException {
        return problemDescriptor.getLoader().load(inputStream);
    }

    public int getRepeats() {
        return repeats;
    }

    public void setRepeats(int repeats) {
        this.repeats = repeats;
    }

    public int getTermination() {
        return termination;
    }

    public void setTermination(int termination) {
        this.termination = termination;
    }

    public Map<String, String> getTerminationConfigs() {
        return terminationConfigs;
    }

    public Map<String, String> getMetaConfigs() {
        return metaConfigs;
    }
}
