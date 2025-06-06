package com.ea_framework.Configs;

import com.ea_framework.ACOTypes.ACOType;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public class TSP2DACOConfig implements AlgorithmConfig {

    // Config page for ACO on TSP problems

    // Necessary parameters
    private double alpha;
    private double beta;
    private ACOType type;
    private double[][] distanceMatrix;
    private int[] initialTour;
    private double rho;  // Assuming this field exists for pheromone evaporation rate


    // Populates the config with the necessary parameters, gathgered from the config
    @Override
    public void populate(Map<String, Object> raw, Problem problem) {
        this.alpha = (Double) raw.get("alpha");
        this.beta = (Double) raw.get("beta");
        this.type = (ACOType) raw.get("type");
        this.distanceMatrix = (double[][]) raw.get("distanceMatrix");
        this.initialTour = (int[]) raw.get("initialTour");
        this.rho = (Double) raw.get("rho");
    }


    // Various getters and setters for the defined parameters
    public ACOType getType() { return type; }
    public double[][] getDistanceMatrix() { return distanceMatrix; }
    public int[] getInitialTour() { return initialTour; }


    public double[][] distanceMatrix() { return distanceMatrix; }
    public int[] initialTour() { return initialTour; }
    public ACOType type() { return type; }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getRho() {
        return this.rho;
    }
}
