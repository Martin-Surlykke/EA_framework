package com.ea_framework.Algorithms;

import com.ea_framework.Problems.Problem;

public interface ProblemAware<P extends Problem<?>> {
    void setProblem(P problem);
}