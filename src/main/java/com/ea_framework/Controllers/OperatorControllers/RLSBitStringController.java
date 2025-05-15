package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.MutationFunctions.RLSBitString;

public class RLSBitStringController implements OperatorConfigController {

    @Override
    public Object getOperator() {
        return new RLSBitString();
    }

    @Override
    public boolean isFilled() {
        return true;
    }

}