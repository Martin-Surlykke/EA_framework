package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.FitnessFunctions.TspEuclideanDistance;

public class TSP2DEuclidianDistanceController implements OperatorConfigController {

    @Override
    public Object getConfig() {
        return new TspEuclideanDistance();
    }

    @Override
    public boolean isFilled() {
        return true;
    }
}
