package com.ea_framework.Descriptors;

import com.ea_framework.Loaders.ProblemLoader;
import com.ea_framework.Problems.Problem;

public class ProblemDescriptor implements Descriptor {

    private final String name;
    private final String searchSpaceType;
    private final ProblemLoader loader;

    public ProblemDescriptor(String name,
                             String searchSpaceType,
                             ProblemLoader loader) {
        this.name = name;
        this.searchSpaceType = searchSpaceType;
        this.loader = loader;
    }

    public ProblemLoader getLoader() {
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

    public Problem createProblem(int size) {
        return loader.createFromSize(size);
    }
}