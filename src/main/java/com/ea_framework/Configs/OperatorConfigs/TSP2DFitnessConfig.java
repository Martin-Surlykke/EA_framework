package com.ea_framework.Configs.OperatorConfigs;

public class TSP2DFitnessConfig implements FitnessConfig {

    // Configuration for TSP2D fitness calculation
// This class holds the distance matrix used to calculate the fitness of TSP solutions
    private final double[][] distanceMatrix;

    // Constructor to initialize the distance matrix
    public TSP2DFitnessConfig(double[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    // Getter for the distance matrix
    public double[][] getDistanceMatrix() { return distanceMatrix; }
}