package com.ea_framework.Algorithms;

public interface Algorithms <SolutionType> {
    void run();
    SolutionType getResult();
    void setFirst(SolutionType first);
    int getIterations();
    void setMaxIterations(int maxIterations);

}
