package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.ChoiceFunctions.BitStringGreedyChoice;

public class BitStringGreedyChoiceController implements OperatorConfigController {
    @Override
    public boolean isFilled() {
        return true;
    }

    @Override
    public Object getOperator() {
        return new BitStringGreedyChoice();
    }
}
