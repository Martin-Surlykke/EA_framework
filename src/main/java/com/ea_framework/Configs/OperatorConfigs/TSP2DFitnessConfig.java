package com.ea_framework.Configs.OperatorConfigs;

public class TSP2DFitnessConfig implements FitnessConfig {
    private final double[][] distanceMatrix;
    private final boolean symmetric;
    private final int nodeCount;

    public TSP2DFitnessConfig(double[][] distanceMatrix, boolean symmetric, int nodeCount) {
        this.distanceMatrix = distanceMatrix;
        this.symmetric = symmetric;
        this.nodeCount = nodeCount;
    }

    public double[][] getDistanceMatrix() { return distanceMatrix; }
    public boolean isSymmetric() { return symmetric; }
    public int getNodeCount() { return nodeCount; }
}