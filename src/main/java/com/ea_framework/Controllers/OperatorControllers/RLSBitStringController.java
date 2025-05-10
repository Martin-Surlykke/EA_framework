package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.MutationFunctions.RLSBitString;

public class RLSBitStringController implements OperatorConfigController {

    @Override
    public Object getConfig() {
        return new RLSBitString();
    }

    @Override
    public boolean isFilled() {
        return true;
    }

}