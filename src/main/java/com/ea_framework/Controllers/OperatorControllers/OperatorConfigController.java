package com.ea_framework.Controllers.OperatorControllers;

public interface OperatorConfigController {
    Object getConfig();
    boolean isFilled();
    default void setChangeListener(Runnable onChange) {}

    Object getOperator();
}