package com.ea_framework.Algorithms;

public abstract class Algorithms {

    protected int maxIterations;

    public Algorithms(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public abstract void run();

    public int getIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

}
