package com.ea_framework.StartAlgorithms;

import com.ea_framework.Candidates.bitStringCandidate;

public class LoadPermutationBitString implements StartAlgorithm<bitStringCandidate> {

    @Override
    public void generateStart(bitStringCandidate bitStringCandidate) {
        boolean [] bitString = bitStringCandidate.getBitString();
        bitStringCandidate.setStartBitstring(bitString);
    }
}