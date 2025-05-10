package com.ea_framework.Views.ConfigViews;

import com.ea_framework.Configs.AlgorithmConfigUI;
import javafx.scene.Node;

public interface ConfigView {
    Node getRoot();
    AlgorithmConfigUI getController();
}