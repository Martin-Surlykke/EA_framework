package com.ea_framework;

import com.ea_framework.Algorithms.BitStringAlgorithm;
import com.ea_framework.ChoiceFunctions.BitStringGreedyChoice;
import com.ea_framework.Mutation.RLS_bitString;
import com.ea_framework.Candidates.bitStringCandidate;
import com.ea_framework.FitnessFunctions.BitStringOneMax;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        bitStringCandidate b = new bitStringCandidate("src/main/resources/bitStringFiles/bitString30.txt");

        boolean [] start = b.getBitString();

        RLS_bitString rls = new RLS_bitString();
        BitStringOneMax bitStringOneMax = new BitStringOneMax();
        BitStringGreedyChoice bitStringGreedyChoice = new BitStringGreedyChoice();

        BitStringAlgorithm bitAlgo = new BitStringAlgorithm(100, bitStringOneMax, rls, bitStringGreedyChoice);
        bitAlgo.setCurrentSolution(start);
        bitAlgo.run();
        
    }
}
