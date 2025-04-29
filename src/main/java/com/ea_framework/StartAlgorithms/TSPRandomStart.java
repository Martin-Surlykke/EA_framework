package com.ea_framework.StartAlgorithms;

import com.ea_framework.Candidates.tspCandidate;
import com.ea_framework.View.CandidateView.TspCandidateView;

import java.util.Arrays;
import java.util.Random;

public class TSPRandomStart implements StartAlgorithm<tspCandidate> {
    public static final Random rand = new Random();


    @Override
    public void generateStart(tspCandidate tspCandidate) {

        int [] permutation = tspCandidate.getPermutation();
        int [] startPermutation = Arrays.copyOf(permutation, permutation.length);

        for (int i = 0; i < permutation.length; i++) {
            int randomIndex = rand.nextInt(permutation.length);
            int temp = startPermutation[i];
            startPermutation[i] = startPermutation[randomIndex];
            startPermutation[randomIndex] = temp;
        }

        tspCandidate.setStartPermutation(startPermutation);
    }
}

