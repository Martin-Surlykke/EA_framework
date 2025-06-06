package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.ChoiceFunctions.BitStringGreedyChoice;

public class BitStringGreedyChoiceController implements OperatorConfigController {
    // Small controller for greedy choice function. No variable parameters so always returns true
    @Override
    public boolean isFilled() {
        return true;
    }

    @Override
    public Object getOperator() {
        return new BitStringGreedyChoice();
    }
}
