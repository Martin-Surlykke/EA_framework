package com.ea_framework.Configs.OperatorConfigs;

public class BitStringChoiceConfig implements ChoiceConfig {
    private final double alpha;
    private final double T0;

    public BitStringChoiceConfig() {
        this.alpha = getAlpha();
        this.T0 = getT0();
    }

    public double getAlpha() {
        return alpha;
    }

    public double getT0() {
        return T0;
    }
}
