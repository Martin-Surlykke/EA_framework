package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.MutationFunctions.TSP2DTwoOpt;

public class TSP2DTwoOptController implements OperatorConfigController {

    @Override
    public Object getOperator() {
        return new TSP2DTwoOpt();
    }

    @Override
    public boolean isFilled() {
        return true;
    }
}
