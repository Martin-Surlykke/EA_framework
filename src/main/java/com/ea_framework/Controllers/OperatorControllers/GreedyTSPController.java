package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.ChoiceFunctions.TSP2DGreedyChoice;

import java.util.Comparator;

public class GreedyTSPController implements OperatorConfigController {

    @Override
    public Object getConfig() {
        return new TSP2DGreedyChoice();
    }

    @Override
    public boolean isFilled() {
        return true;
    }
}
