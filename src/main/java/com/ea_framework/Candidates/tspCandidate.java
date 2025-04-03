package com.ea_framework.Candidates;

import com.ea_framework.StartAlgorithms.StartAlgorithm;

import java.util.Optional;

public class tspCandidate extends Candidate{

    @Override
    public Candidate clone() {
        return null;
    }


    @Override
    public void setStartCandidate(String filePath, Optional<StartAlgorithm> startAlgorithm) {

    }

    @Override
    public String stringify() {
        return "";
    }
}
