package com.ea_framework.StartAlgorithms;

import com.ea_framework.Candidates.tspCandidate;

public class TspFromStartToEnd implements StartAlgorithm<tspCandidate> {

    @Override
    public void generateStart(tspCandidate tspCandidate) {
        int[] permutation = tspCandidate.getPermutation();

        tspCandidate.setStartPermutation(permutation);

    }
}
