package com.ea_framework.FitnessFunctions;

public abstract class GraphFitness implements Fitness<Integer [][], Double> {

    protected double [][] distanceMatrix;
    public GraphFitness(double [][] DM) {
        this.distanceMatrix = DM;

    }
    @Override
    public Double evaluate(Integer[][] solution) {
        return(evaluateGraph(solution));
    }

    public abstract double evaluateGraph(Integer[][] solution);
}
