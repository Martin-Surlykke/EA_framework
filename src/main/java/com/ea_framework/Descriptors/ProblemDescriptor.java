package com.ea_framework.Descriptors;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Loaders.ProblemLoader;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Runners.ProblemRunner;

public class ProblemDescriptor implements Descriptor {
    private final String name;
    private final String searchSpaceType;
    private final ProblemLoader<? extends Problem<?>> loader;

    private final ProblemRunner<Problem, Algorithm<?>> runner;

    public ProblemDescriptor(String name, String searchSpaceType, ProblemLoader loader, ProblemRunner<Problem, Algorithm<?>> runner) {
        this.name = name;
        this.searchSpaceType = searchSpaceType;
        this.loader = loader;
        this.runner = runner;
    }

    public ProblemLoader getLoader() {
        return loader;
    }

    @Override public String getName() {
        return name;
    }

    @Override public String getCompatibleKey() {
        return searchSpaceType;
    }
    }