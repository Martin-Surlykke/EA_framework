package com.ea_framework;

import com.ea_framework.Algorithms.BitStringAlgorithm;
import com.ea_framework.Algorithms.One_One_EA_BitString;
import com.ea_framework.ChoiceFunctions.BitStringGreedyChoice;
import com.ea_framework.ChoiceFunctions.BitStringSimulatedAnnealing;
import com.ea_framework.FitnessFunctions.BitStringLeadingOnes;
import com.ea_framework.Candidates.bitStringCandidate;
import com.ea_framework.FitnessFunctions.BitStringOneMax;
import com.ea_framework.Mutation.RLS_bitString;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        bitStringCandidate b = new bitStringCandidate("src/main/resources/bitStringFiles/bitString30.txt");

        boolean [] start = b.getBitString();
        System.out.println(b.stringify());

        RLS_bitString rls = new RLS_bitString();
        BitStringOneMax oneMax = new BitStringOneMax();

        BitStringGreedyChoice greedy = new BitStringGreedyChoice();
       // annealing.setAlpha(0.95);
      //  annealing.setT0(10);
        BitStringAlgorithm bitAlgo = new BitStringAlgorithm(100, oneMax, rls, greedy);
        bitAlgo.setCurrentSolution(start);
        bitAlgo.run();
        start = bitAlgo.getCurrentSolution();
        b.setBitString(start);
        System.out.println(b.stringify());
        
    }
}
