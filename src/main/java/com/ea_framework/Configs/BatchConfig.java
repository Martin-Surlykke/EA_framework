package com.ea_framework.Configs;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Problems.Problem;
import com.ea_framework.SearchSpaces.SearchSpace;
import com.ea_framework.Termination.TerminationCondition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchConfig {

    // Config class used to store all necessary configurations for running a batch of algorithms on a problem.

    // streamName is used to identify the input stream for the problem.
    private String streamName;

    // inputFile is the file from which the problem will be loaded.
    private File inputFile;


    // searchSpaceName is the name of the search space to be used.
    private String searchSpaceName;

    // searchSpace is the actual search space object that will be used in the algorithm.
    private SearchSpace searchSpace;

    // problemName is the name of the problem to be solved.
    private String problemName;

    // problemDescriptor is the descriptor that provides metadata and loading capabilities for the problem.
    private ProblemDescriptor problemDescriptor;

    // algorithmName is the name of the algorithm to be used.
    private String algorithmName;

    // algorithmDescriptor is the descriptor that provides metadata and creation capabilities for the algorithm.
    private AlgorithmDescriptor algorithmDescriptor;


    // algorithmConfig holds the configuration parameters for the algorithm.
    private AlgorithmConfig algorithmConfig;


    // terminationConfigs holds a list of configurations for termination conditions.
    private final Map<String, String> terminationConfigs = new HashMap<>();

    // metaConfigs holds additional metadata configurations that might be used during the run.
    private final Map<String, String> metaConfigs = new HashMap<>();


    // problemSize is the size of the problem to be solved, if applicable.
    private int problemSize;


    // showVisualization indicates whether the visualization should be shown during the run.
    private boolean showVisualization = true;


    // repeats indicates how many times the algorithm should be run on the problem.
    private int repeats;


    private TerminationCondition termination;

    // terminationConditions is a list of termination conditions that will be checked during the algorithm run.
    private List<TerminationCondition> terminationConditions = new ArrayList<>();


    // Various getters and setters for the configurations associated with the batch run.
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

    public TerminationCondition getTermination() {
        return termination;
    }

    public void setTermination(TerminationCondition termination) {
        this.termination = termination;
    }

    public Map<String, String> getTerminationConfigs() {
        return terminationConfigs;
    }

    public Map<String, String> getMetaConfigs() {
        return metaConfigs;
    }


    // Resolve problem creates a problem based on the provided input file or problem size.
    // Given a problem size is added, a random problem of that size will be created.
    public Problem resolveProblem() throws IOException {
        if (problemDescriptor == null)
            throw new IllegalStateException("Problem descriptor is not set.");

        if (problemSize > 0) {
            return problemDescriptor.createProblem(problemSize);
        }

        if (inputFile == null) {
            throw new FileNotFoundException("No input file specified and no problem size given.");
        }

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

    public boolean getVisualSelected() {
        return showVisualization;
    }
    public void setVisualSelected(boolean showVisualization) {
        this.showVisualization = showVisualization;
    }


    public List<TerminationCondition> getTerminationConditions() {
        return terminationConditions;
    }

    public void setProblemSize(int size) {
        this.problemSize = size;
    }

    public int getProblemSize() {
        return problemSize;
    }
}
