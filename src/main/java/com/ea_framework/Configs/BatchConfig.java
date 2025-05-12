package com.ea_framework.Configs;


import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Filehandlers.FileLoader;
import com.ea_framework.Problems.Problem;
import com.ea_framework.SearchSpaces.SearchSpace;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BatchConfig {
    private String fileLoaderName;
    private FileLoader<?> fileLoader;

    private String streamName;
    private InputStream inputStream;

    private String searchSpaceName;
    private SearchSpace<?> searchSpace;

    private String problemName;

    private String algorithmName;
    private AlgorithmDescriptor<?, ?> algorithmDescriptor;

    private Map<String, Object> algorithmConfig = new HashMap<>();

    private Map<String, String> terminationConfigs = new HashMap<>();

    private Map<String, String> metaConfigs = new HashMap<>();

    private int repeats;

    private int termination;

    private Problem problem;

    private ProblemDescriptor problemDescriptor;

    public SearchSpace<?> getSearchSpace() {
        return searchSpace;
    }

    public String getFileLoaderName() {
        return fileLoaderName;
    }

    public void setFileLoaderName(String fileLoaderName) {
        this.fileLoaderName = fileLoaderName;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getSearchSpaceName() {
        return searchSpaceName;
    }

    public void setSearchSpaceName(String searchSpaceName) {
        this.searchSpaceName = searchSpaceName;
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

    public void setSearchSpace(SearchSpace<?> searchSpace) {
        this.searchSpace = searchSpace;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public AlgorithmDescriptor<?, ?> getAlgorithmDescriptor() {
        return algorithmDescriptor;
    }

    public void setAlgorithmDescriptor(AlgorithmDescriptor<?, ?> descriptor) {
        this.algorithmDescriptor = descriptor;
    }

    public Map<String, Object> getAlgorithmConfig() {
        return algorithmConfig;
    }

    public Map<String, String> getTerminationConfigs() {
        return terminationConfigs;
    }

    public Map<String, String> getMetaConfigs() {
        return metaConfigs;
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

    public void setFileLoader(FileLoader<Problem> loader) {
        this.fileLoader = loader;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public Problem getProblem() {
        return problem;
    }

    public FileLoader getFileLoader() {
        return fileLoader;
    }

    public void setProblemDescriptor(ProblemDescriptor problemShell) {
        this.problemDescriptor = problemShell;
    }

    public void setInputStream(InputStream stream) {
    }
}