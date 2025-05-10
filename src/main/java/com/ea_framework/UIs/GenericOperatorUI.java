package com.ea_framework.UIs;

import com.ea_framework.Controllers.OperatorControllers.OperatorConfigController;
import javafx.scene.Node;
import javafx.scene.Parent;

import java.util.Collection;

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

    public Parent getRoot() {
        return view;
    }
}