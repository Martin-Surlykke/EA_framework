package com.ea_framework;

import com.ea_framework.Algorithms.BitStringAlgorithm;
import com.ea_framework.Algorithms.One_One_EA_BitString;
import com.ea_framework.ChoiceFunctions.BitStringGreedyChoice;
import com.ea_framework.ChoiceFunctions.BitStringSimulatedAnnealing;
import com.ea_framework.FitnessFunctions.BitStringLeadingOnes;
import com.ea_framework.Candidates.bitStringCandidate;

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {
        bitStringCandidate b = new bitStringCandidate("src/main/resources/bitStringFiles/bitString30.txt");

        boolean [] start = b.getBitString();
        System.out.println(b.stringify());

        One_One_EA_BitString ea = new One_One_EA_BitString();
        BitStringLeadingOnes leadingOnes = new BitStringLeadingOnes();
        BitStringSimulatedAnnealing annealing = new BitStringSimulatedAnnealing();
        annealing.setAlpha(0.05);
        annealing.setT0(20);
        BitStringAlgorithm bitAlgo = new BitStringAlgorithm(200, leadingOnes, ea, annealing);
        bitAlgo.setCurrentSolution(start);
        bitAlgo.run();
        start = bitAlgo.getCurrentSolution();
        b.setBitString(start);
        System.out.println(b.stringify());
        
    }
}
