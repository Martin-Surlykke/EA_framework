package com.ea_framework.Algorithms;

public interface Algorithm<SolutionType> {
    void run(int iteration);
    SolutionType getCurrentSolution();
    void setFirst(SolutionType first);

}
