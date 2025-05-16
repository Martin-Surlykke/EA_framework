package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.MutationFunctions.BitStringRLS;

public class BitStringRLSController implements OperatorConfigController {

    @Override
    public Object getOperator() {
        return new BitStringRLS();
    }

    @Override
    public boolean isFilled() {
        return true;
    }

}