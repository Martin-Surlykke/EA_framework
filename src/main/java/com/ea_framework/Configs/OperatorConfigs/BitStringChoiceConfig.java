package com.ea_framework.Configs.OperatorConfigs;

public class BitStringChoiceConfig implements ChoiceConfig {

    // Configuration for BitStringChoice which is used in Simulated Annealing
    private final double alpha;
    private final double T0;

    // Constructor to initialize the configuration with default values
    public BitStringChoiceConfig() {
        this.alpha = getAlpha();
        this.T0 = getT0();
    }


    // getters for applying user-defined parameters
    public double getAlpha() {
        return alpha;
    }

    public double getT0() {
        return T0;
    }
}
