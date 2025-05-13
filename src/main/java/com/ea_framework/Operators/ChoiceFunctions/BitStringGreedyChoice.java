package com.ea_framework.Operators.ChoiceFunctions;

import com.ea_framework.Configs.OperatorConfigs.BitStringChoiceConfig;
import com.ea_framework.Configurable;

public class BitStringGreedyChoice implements ChoiceFunction<boolean[], Integer>, Configurable<BitStringChoiceConfig> {
    @Override
    public void configure(BitStringChoiceConfig config) {

    }

    @Override
    public boolean[] choose(boolean[] current, boolean[] candidate, Integer fitnessCurrent, Integer fitnessCandidate, int iteration) {
        return fitnessCandidate > fitnessCurrent ? candidate : current;
    }
}
