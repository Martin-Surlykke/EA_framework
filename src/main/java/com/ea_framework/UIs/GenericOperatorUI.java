package com.ea_framework.UIs;

import com.ea_framework.Controllers.OperatorControllers.OperatorConfigController;
import javafx.scene.Parent;

public class GenericOperatorUI {
    private final Parent view;
    private final OperatorConfigController controller;

    public GenericOperatorUI(Parent view, OperatorConfigController controller) {
        this.view = view;
        this.controller = controller;
    }

    public Parent getView() {
        return view;
    }

    public OperatorConfigController getController() {
        return controller;
    }
}