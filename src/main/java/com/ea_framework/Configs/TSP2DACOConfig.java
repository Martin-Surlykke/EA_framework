package com.ea_framework.Configs;

import com.ea_framework.ACOTypes.ACOType;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public class TSP2DACOConfig implements AlgorithmConfig {

    private double alpha;
    private double beta;
    private ACOType type;
    private double[][] distanceMatrix;
    private int[] initialTour;

    @Override
    public void populate(Map<String, Object> raw, Problem problem) {
        this.alpha = (Double) raw.get("alpha");
        this.beta = (Double) raw.get("beta");
        this.type = (ACOType) raw.get("type");
        this.distanceMatrix = (double[][]) raw.get("distanceMatrix");
        this.initialTour = (int[]) raw.get("initialTour");
    }

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
}
