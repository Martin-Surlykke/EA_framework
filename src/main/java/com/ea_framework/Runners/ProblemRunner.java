package com.ea_framework.Runners;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Controllers.VisualizeController;
import com.ea_framework.Problems.Problem;

public interface ProblemRunner<P extends Problem, A extends Algorithm<?>> {
    void run(P problem, A algorithm, VisualizeController<?> controller, int termination);
}