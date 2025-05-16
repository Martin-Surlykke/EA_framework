package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.FitnessFunctions.BitStringOneMax;

public class BitStringOneMaxController implements OperatorConfigController {
    @Override
    public boolean isFilled() {
        return true;
    }

    @Override
    public Object getOperator() {
        return new BitStringOneMax();
    }
}
