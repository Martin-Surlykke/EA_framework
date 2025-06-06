package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.ChoiceFunctions.TSP2DGreedyChoice;

public class TSP2DGreedyChoiceController implements OperatorConfigController {

    // Greedy choice function for TSP problems
    @Override
    public Object getOperator() {
        return new TSP2DGreedyChoice();
    }

    @Override
    public boolean isFilled() {
        return true;
    }
}
