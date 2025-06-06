package com.ea_framework.Configs.OperatorConfigs;

public class TSP2DChoiceConfig implements ChoiceConfig {


    // Configuration for TSP 2D choice operator
    // This class holds the parameters for the Choice operator used in Simulated Annealing
    private double alpha;
    private double T0;

    // Getters and Setters for applying the configuration
    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getT0() {
        return T0;
    }

    public void setT0(double T0) {
        this.T0 = T0;
    }


}
