package com.ea_framework.Candidates;
import java.io.IOException;

public interface Candidate {

    void setStartCandidate(String filePath) throws IOException;

    String stringify();
}
