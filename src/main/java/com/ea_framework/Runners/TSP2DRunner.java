package com.ea_framework.Runners;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Algorithms.TSPAlgorithm;
import com.ea_framework.Controllers.VisualizeController;
import com.ea_framework.Problems.TSP2DProblem;

public class TSP2DRunner<A extends Algorithm<int[]>> implements ProblemRunner<TSP2DProblem, A> {
    private final DefaultRunner<int[]> delegate = new DefaultRunner<>();

    @Override
    public void run(TSP2DProblem problem, A algorithm, VisualizeController controller, int termination) {
        algorithm.setDistanceMatrix(problem.getDistanceMatrix()); // TSP-specific setup
        delegate.run(problem, algorithm, controller, termination); // shared runner logic
    }

    @Override
    public void run(TSP2DProblem problem, A algorithm, VisualizeController<?> controller, int termination) {
    }
}