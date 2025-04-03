package com.ea_framework.Candidates;
import com.ea_framework.StartAlgorithms.StartAlgorithm;

import java.io.IOException;
import java.util.Optional;

public abstract class Candidate {

    public abstract Candidate clone();
    public abstract void setStartCandidate(String filePath, Optional<StartAlgorithm> startAlgorithm) throws IOException;


    public abstract String stringify();
}
