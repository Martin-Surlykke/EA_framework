package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.FitnessFunctions.BitStringOneMax;

public class BitStringOneMaxController implements OperatorConfigController {
    // Controller class for the OneMax Function, this is deprecated as fitness has been moved
    // To the problem in terms of bit strings
    @Override
    public boolean isFilled() {
        return true;
    }

    @Override
    public Object getOperator() {
        return new BitStringOneMax();
    }
}
