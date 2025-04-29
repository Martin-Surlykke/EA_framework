package com.ea_framework;

import com.ea_framework.Algorithms.BitStringAlgorithm;
import com.ea_framework.Algorithms.TSPAlgorithm;
import com.ea_framework.Candidates.bitStringCandidate;
import com.ea_framework.Candidates.tspCandidate;
import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.ChoiceFunctions.GreedyChoice;
import com.ea_framework.FitnessFunctions.BitStringLeadingOnes;
import com.ea_framework.FitnessFunctions.DistanceMatrixContext;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.FitnessFunctions.TspEuclidianDistance;
import com.ea_framework.Mutation.MutationOperator;
import com.ea_framework.Mutation.RLSBitString;
import com.ea_framework.Mutation.TwoOptTsp;

import java.io.IOException;
import java.util.Comparator;

public class main {

    private static final int MAX_ITERATIONS = 100;
    public static void main(String[] args) throws IOException {
        bitStringCandidate b = new bitStringCandidate("src/main/resources/bitStringFiles/bitString30.txt");
        BitStringLeadingOnes leadingOnes = new BitStringLeadingOnes();
        GreedyChoice<boolean[], Integer> greedyChoice = new GreedyChoice<boolean[], Integer>(Comparator.naturalOrder());
        RLSBitString rls = new RLSBitString();
        BitStringAlgorithm bitAlgo = new BitStringAlgorithm(leadingOnes, rls, greedyChoice);

        bitAlgo.setCurrentSolution(b.getBitString());

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            bitAlgo.run(i);
            b.setBitString(bitAlgo.getCurrentSolution());
        }

        System.out.println(b.stringify());

        System.out.println();


        
    }
}
