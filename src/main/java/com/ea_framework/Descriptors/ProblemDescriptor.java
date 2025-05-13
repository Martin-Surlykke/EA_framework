package com.ea_framework.Descriptors;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Loaders.ProblemLoader;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Runners.Runner;


public class ProblemDescriptor<
        S,
        P extends Problem<S>
        > implements Descriptor {

    private final String name;
    private final String searchSpaceType;
    private final ProblemLoader<P> loader;

    public ProblemDescriptor(String name,
                             String searchSpaceType,
                             ProblemLoader<P> loader) {
        this.name = name;
        this.searchSpaceType = searchSpaceType;
        this.loader = loader;
    }

    public ProblemLoader<P> getLoader() {
        return loader;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCompatibleKey() {
        return searchSpaceType;
    }
}
