package com.ea_framework.Configs;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Problems.Problem;
import com.ea_framework.SearchSpaces.SearchSpace;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BatchConfig {

    private String streamName;
    private File inputFile; // Changed from InputStream

    private String searchSpaceName;
    private SearchSpace searchSpace;

    private String problemName;
    private ProblemDescriptor problemDescriptor;

    private String algorithmName;
    private AlgorithmDescriptor algorithmDescriptor;

    private AlgorithmConfig algorithmConfig;

    private final Map<String, String> terminationConfigs = new HashMap<>();
    private final Map<String, String> metaConfigs = new HashMap<>();
    private Map<String, Object> rawOperatorConfigs = new HashMap<>();

    private int repeats;
    private int termination;

    // === Getters and Setters ===

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(inputFile); // Always fresh stream
    }

    public String getSearchSpaceName() {
        return searchSpaceName;
    }

    public void setSearchSpaceName(String searchSpaceName) {
        this.searchSpaceName = searchSpaceName;
    }

    public SearchSpace getSearchSpace() {
        return searchSpace;
    }

    public void setSearchSpace(SearchSpace searchSpace) {
        this.searchSpace = searchSpace;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public ProblemDescriptor getProblemDescriptor() {
        return problemDescriptor;
    }

    public void setProblemDescriptor(ProblemDescriptor problemDescriptor) {
        this.problemDescriptor = problemDescriptor;
    }

    public AlgorithmDescriptor getAlgorithmDescriptor() {
        return algorithmDescriptor;
    }

    public void setAlgorithmDescriptor(AlgorithmDescriptor algorithmDescriptor) {
        this.algorithmDescriptor = algorithmDescriptor;
    }

    public AlgorithmConfig getAlgorithmConfig() {
        return algorithmConfig;
    }

    public void setAlgorithmConfig(AlgorithmConfig algorithmConfig) {
        this.algorithmConfig = algorithmConfig;
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

    public void setRawOperatorConfigs(Map<String, Object> configs) {
        this.rawOperatorConfigs = configs;
    }

    public Map<String, Object> getRawOperatorConfigs() {
        return rawOperatorConfigs;
    }

    public Problem resolveProblem() throws IOException {
        try (InputStream in = getInputStream()) {
            return problemDescriptor.getLoader().load(in);
        }
    }

    public Algorithm resolveAlgorithm() {
        return algorithmDescriptor.create(algorithmConfig);
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}
