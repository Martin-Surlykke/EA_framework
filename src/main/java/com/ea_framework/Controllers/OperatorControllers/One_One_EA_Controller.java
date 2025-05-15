package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.MutationFunctions.One_One_EA_BitString;

public class One_One_EA_Controller implements OperatorConfigController{


    @Override
    public Object getOperator() {
        return new One_One_EA_BitString();
    }

    @Override
    public boolean isFilled() {
        return true;
    }


}
