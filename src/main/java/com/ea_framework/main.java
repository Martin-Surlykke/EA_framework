package com.ea_framework;

import com.ea_framework.Algorithms.BitStringAlgorithm;
import com.ea_framework.Candidates.bitStringCandidate;
import com.ea_framework.Candidates.tspCandidate;
import com.ea_framework.ChoiceFunctions.BitStringGreedyChoice;
import com.ea_framework.FitnessFunctions.BitStringLeadingOnes;
import com.ea_framework.Mutation.RLSBitString;
import com.ea_framework.StartAlgorithms.LoadPermutationBitString;
import com.ea_framework.StartAlgorithms.TspFromStartToEnd;

import java.io.IOException;
import java.util.Arrays;

public class main {
    public static void main(String[] args) throws IOException {

        bitStringCandidate b = new bitStringCandidate("src/main/resources/bitStringFiles/bitString30.txt");
        BitStringLeadingOnes leadingOnes = new BitStringLeadingOnes();
        BitStringGreedyChoice greedyChoice = new BitStringGreedyChoice();
        RLSBitString rls = new RLSBitString();
        BitStringAlgorithm algo = new BitStringAlgorithm(leadingOnes, rls, greedyChoice);
        algo.setFirst(b.getBitString());
        algo.setMaxIterations(100);
        System.out.println(Arrays.toString(algo.getCurrentSolution()));
        algo.run();
        b.setBitString(algo.getCurrentSolution());
        System.out.println(b.stringify());


        System.out.println();

        TspFromStartToEnd start = new TspFromStartToEnd();
        tspCandidate t = new tspCandidate("src/main/resources/tspFiles/st70.tsp");
        System.out.println(t.stringify());


        
    }
}
