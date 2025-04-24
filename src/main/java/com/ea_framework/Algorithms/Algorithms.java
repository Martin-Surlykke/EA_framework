package com.ea_framework.Algorithms;

public interface Algorithms <SolutionType> {
    void run(int iteration);
    SolutionType getResult();
    void setFirst(SolutionType first);
}
