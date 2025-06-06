package com.ea_framework.Controllers.OperatorControllers;

public interface OperatorConfigController {

    // Interface for Operator configs

    // boolean isFilled to ensure the necessary fields are filled before the operator can be collected
    boolean isFilled();

    // ChangeListener handles changes made to values in operator fields, ensuring values are updated in the framework
    default void setChangeListener(Runnable onChange) {}

    // Returns an instance of the operator where the chosen parameters are assigned.
    Object getOperator();
}