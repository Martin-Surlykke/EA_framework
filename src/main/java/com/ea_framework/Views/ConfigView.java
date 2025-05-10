package com.ea_framework.Views;

import com.ea_framework.Configs.AlgorithmConfigUI;
import javafx.scene.Node;

public class ConfigView {
    private final Node root;
    private final AlgorithmConfigUI controller;

    public ConfigView(Node root, AlgorithmConfigUI controller) {
        this.root = root;
        this.controller = controller;
    }

    public Node getRoot() {
        return root;
    }

    public AlgorithmConfigUI getController() {
        return controller;
    }
}