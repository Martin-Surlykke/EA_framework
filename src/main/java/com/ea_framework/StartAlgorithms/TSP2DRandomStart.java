package com.ea_framework.StartAlgorithms;

import com.ea_framework.Problems.TSP2DProblem;

import java.util.Arrays;
import java.util.Random;

public class TSP2DRandomStart implements StartAlgorithm<TSP2DProblem, int[]> {
    public static final Random rand = new Random();

    @Override
    public int[] generateFirstSolution(TSP2DProblem problem) {
        Random rand = new Random();
        int[] startPermutation = problem.getDefaultPermutation().clone();
        for (int i = 0; i < startPermutation.length; i++) {
            int randomIndex = rand.nextInt(startPermutation.length);
            int temp = startPermutation[i];
            startPermutation[i] = startPermutation[randomIndex];
            startPermutation[randomIndex] = temp;
        }
        return startPermutation;
    }
}

