package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.FitnessFunctions.TspEuclidianDistance;

public class TSP2DEuclidianDistanceController implements OperatorConfigController {

    @Override
    public Object getConfig() {
        return new TspEuclidianDistance();
    }

    @Override
    public boolean isFilled() {
        return true;
    }
}
