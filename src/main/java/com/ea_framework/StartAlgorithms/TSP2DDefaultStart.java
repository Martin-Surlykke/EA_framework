package com.ea_framework.StartAlgorithms;

import com.ea_framework.Problems.TSP2DProblem;

public class TSP2DDefaultStart implements StartAlgorithm<TSP2DProblem, int[]> {
    @Override
    public int[] generateFirstSolution(TSP2DProblem problem) {
        return problem.getDefaultPermutation().clone();
    }
}
