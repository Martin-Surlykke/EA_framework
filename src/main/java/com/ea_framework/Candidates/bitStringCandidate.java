package com.ea_framework.Candidates;
import com.ea_framework.Filehandlers.BitStringFileHandler;
import com.ea_framework.StartAlgorithms.LoadPermutationBitString;
import com.ea_framework.StartAlgorithms.StartAlgorithm;

import java.io.IOException;
import java.util.Optional;


public class bitStringCandidate implements Candidate{
    private boolean [] bitString;

    @Override
    public void setStartCandidate(String filePath) throws IOException {
        bitString = BitStringFileHandler.readFile(filePath);
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
        setStartCandidate(filePath);
    }

    public boolean[] getBitString() {
        return bitString;
    }

    public void setBitString(boolean[] input) {
        this.bitString = input;
    }
}
