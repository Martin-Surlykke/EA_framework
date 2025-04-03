package com.ea_framework.Candidates;
import com.ea_framework.Filehandlers.BitStringFileHandler;
import com.ea_framework.StartAlgorithms.StartAlgorithm;

import java.io.IOException;
import java.util.Optional;


public class bitStringCandidate extends Candidate{
    private boolean [] bitString;

    @Override
    public Candidate clone() {
        return null;
    }

    @Override
    public void setStartCandidate(String filePath, Optional<StartAlgorithm> startAlgorithm) throws IOException {
        bitString = BitStringFileHandler.readFile(filePath);
        startAlgorithm.ifPresent(algorithm -> {
            // Add possibility for adding a Algorithm to start permutation
        });
    }

    @Override
    public String stringify() {
        StringBuilder sb = new StringBuilder();
        for (boolean bit : bitString) {
            sb.append(bit ? '1' : '0');
        }
        return sb.toString();
    }

    public bitStringCandidate(String filePath) throws IOException {
        setStartCandidate(filePath, Optional.empty());
    }

    public boolean[] getBitString() {
        return bitString;
    }

    public void setBitString(boolean[] input) {
        this.bitString = input;
    }
}
