package com.ea_framework.ChoiceFunctions;

import java.awt.*;

public class BitStringGreedyChoice implements ChoiceFunction <boolean [], Integer> {

    @Override
    public boolean[] choose(boolean[] current, boolean[] candidate, Integer fitnessCurrent, Integer fitnessCandidate, int iteration) {
        return fitnessCandidate > fitnessCurrent ? candidate : current;
    }
}
