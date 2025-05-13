package com.ea_framework.StartAlgorithms;

import com.ea_framework.Problems.BitStringProblem;

    public class BitStringDefaultStart implements StartAlgorithm <BitStringProblem, boolean[]> {
        @Override
        public boolean[] generateFirstSolution(BitStringProblem problem) {
            return problem.getDefaultPermutation().clone();
        }
    }