package com.ea_framework.Views.VisualizeView;

import com.ea_framework.Configs.AlgorithmConfig;
import javafx.scene.Node;

public interface VisualizeView {
    Node getView();
    void update(Object solution);
    void applyConfig(AlgorithmConfig config);
}
