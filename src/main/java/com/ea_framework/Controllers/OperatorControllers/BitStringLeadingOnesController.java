package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.FitnessFunctions.BitStringLeadingOnes;

public class BitStringLeadingOnesController implements OperatorConfigController {
    @Override
    public boolean isFilled() {
        return true;
    }

    @Override
    public Object getOperator() {
        return new BitStringLeadingOnes();
    }
}
