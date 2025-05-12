package com.ea_framework;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.BatchConfig;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Problems.Problem;
import com.ea_framework.SearchSpaces.SearchSpace;

public class RunBatch {

    private final BatchConfig config;
    private final int repeats;
    private final int termination;

    public RunBatch(BatchConfig config) {
        this.config = config;
        this.repeats = config.getRepeats();
        this.termination = config.getTermination();
    }

    public void run() {
        Problem problem = config.getProblem();
        System.out.println(problem.getName());
    }
}