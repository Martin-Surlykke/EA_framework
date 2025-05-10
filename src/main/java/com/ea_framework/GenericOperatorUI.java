package com.ea_framework;

import javafx.scene.Node;

import java.util.Map;

public interface GenericOperatorUI {
    Node getRoot();
    Map<String, Object> getConfigs();
    void loadConfigs(Map<String, Object> config);
}
