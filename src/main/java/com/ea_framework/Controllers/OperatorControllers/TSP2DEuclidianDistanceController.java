package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Controllers.OperatorControllers.OperatorConfigController;

import java.util.HashMap;
import java.util.Map;

public class TSP2DEuclidianDistanceController implements OperatorConfigController {

    private Object distanceMatrix;

    public void setDistanceMatrix(Object matrix) {
        this.distanceMatrix = matrix;
    }

    @Override
    public Map<String, Object> getConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("distanceMatrix", distanceMatrix);
        return config;
    }

    @Override
    public boolean isFilled() {
        return distanceMatrix != null;
    }

    @Override
    public Object getOperator() {
        return null;
    }
}
