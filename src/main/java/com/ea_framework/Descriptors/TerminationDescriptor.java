package com.ea_framework.Descriptors;

import com.ea_framework.Termination.TerminationCondition;
import com.ea_framework.UIs.GenericOperatorUI;
import com.ea_framework.Controllers.OperatorControllers.OperatorConfigController;
import javafx.scene.Parent;

import java.util.function.Supplier;

public class TerminationDescriptor {
    private final String name;
    private final String fxmlPath;
    private final Supplier<GenericOperatorUI> uiSupplier;
    private final Supplier<TerminationCondition> instanceSupplier;

    public TerminationDescriptor(String name, String fxmlPath, Supplier<GenericOperatorUI> uiSupplier, Supplier<TerminationCondition> instanceSupplier) {
        this.name = name;
        this.fxmlPath = fxmlPath;
        this.uiSupplier = uiSupplier;
        this.instanceSupplier = instanceSupplier;
    }

    public String getName() {
        return name;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }

    public GenericOperatorUI createUI() {
        return uiSupplier.get();
    }

    public TerminationCondition createInstance() {
        return instanceSupplier.get();
    }
}
