package com.ea_framework.Configs.OperatorConfigs;

public class TSP2DFitnessConfig implements FitnessConfig {
    private final double[][] distanceMatrix;

    public TSP2DFitnessConfig(double[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public double[][] getDistanceMatrix() { return distanceMatrix; }
}