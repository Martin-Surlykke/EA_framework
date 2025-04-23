package com.ea_framework;

import com.ea_framework.Algorithms.BitStringAlgorithm;
import com.ea_framework.Algorithms.One_One_EA_BitString;
import com.ea_framework.Candidates.tspCandidate;
import com.ea_framework.ChoiceFunctions.BitStringGreedyChoice;
import com.ea_framework.ChoiceFunctions.BitStringSimulatedAnnealing;
import com.ea_framework.FitnessFunctions.BitStringLeadingOnes;
import com.ea_framework.Candidates.bitStringCandidate;
import com.ea_framework.FitnessFunctions.BitStringOneMax;
import com.ea_framework.Mutation.RLS_bitString;
import com.ea_framework.StartAlgorithms.RandomPermutation;
import com.ea_framework.StartAlgorithms.StartAlgorithm;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        bitStringCandidate b = new bitStringCandidate("src/main/resources/bitStringFiles/bitString30.txt");

        boolean [] start = b.getBitString();
        System.out.println(b.stringify());

        RLS_bitString rls = new RLS_bitString();
        BitStringLeadingOnes leadingOnes = new BitStringLeadingOnes();

        BitStringGreedyChoice greedy = new BitStringGreedyChoice();

        BitStringAlgorithm bitAlgo = new BitStringAlgorithm(100, leadingOnes, rls, greedy);
        bitAlgo.setCurrentSolution(start);
        bitAlgo.run();
        start = bitAlgo.getCurrentSolution();
        b.setBitString(start);
        System.out.println(b.stringify());


        StartAlgorithm random = new RandomPermutation();
        tspCandidate t = new tspCandidate("src/main/resources/tspFiles/test9.tsp", random);

    }
}
