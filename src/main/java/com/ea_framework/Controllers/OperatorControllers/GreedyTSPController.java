package com.ea_framework.Controllers.OperatorControllers;

import java.util.Comparator;

public class GreedyTSPController implements OperatorConfigController {

    @Override
    public Object getConfig() {
        Comparator<Double> comparator = Comparator.reverseOrder();
        return new GreedyChoice<int[], Double>(comparator);
    }

    @Override
    public boolean isFilled() {
        return true;
    }
}
