package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.MutationFunctions.BitStringOneOneEA;

public class BitStringOneOneEAController implements OperatorConfigController{


    @Override
    public Object getOperator() {
        return new BitStringOneOneEA();
    }

    @Override
    public boolean isFilled() {
        return true;
    }


}
