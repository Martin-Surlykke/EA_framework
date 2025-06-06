package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.FitnessFunctions.BitStringLeadingOnes;

public class BitStringLeadingOnesController implements OperatorConfigController {
    // Controller class for the LeadingOnesFitness Function, this is deprecated as fitness has been moved
    // To the problem in terms of bit strings
    @Override
    public boolean isFilled() {
        return true;
    }

    @Override
    public Object getOperator() {
        return new BitStringLeadingOnes();
    }
}
