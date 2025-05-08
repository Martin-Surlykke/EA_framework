package com.ea_framework.Algorithms;

public interface Algorithms <SolutionType> {
    void run(int iteration);
    SolutionType getCurrentSolution();
    void setFirst(SolutionType first);

}
