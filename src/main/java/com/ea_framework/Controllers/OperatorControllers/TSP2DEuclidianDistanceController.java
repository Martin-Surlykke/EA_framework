package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.FitnessFunctions.TspEuclideanDistance;

public class TSP2DEuclidianDistanceController implements OperatorConfigController {

    // Operator controller for euclidian distance fitness function
    @Override
    public Object getOperator() {
        return new TspEuclideanDistance();
    }

    @Override
    public boolean isFilled() {
        return true;
    }
}
