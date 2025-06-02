package com.ea_framework.StartAlgorithms;

import com.ea_framework.Problems.BitStringCompatible;

    public class BitStringDefaultStart implements StartAlgorithm <BitStringCompatible, boolean[]> {

        @Override
        public boolean[] generateFirstSolution(BitStringCompatible problem) {
            return problem.getDefaultPermutation();
        }
    }