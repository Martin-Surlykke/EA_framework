package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.MutationFunctions.BitStringRLS;

public class BitStringRLSController implements OperatorConfigController {

    // RLS have no parameters so always returns true
    @Override
    public Object getOperator() {
        return new BitStringRLS();
    }

    @Override
    public boolean isFilled() {
        return true;
    }

}