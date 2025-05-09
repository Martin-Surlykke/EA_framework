package com.ea_framework.Views.ConfigViews;

import com.ea_framework.Configs.AlgorithmConfig;
import javafx.scene.Node;

public interface ConfigView {
    Node getUI();                    // the visual component to embed in your scene (JavaFX)
    AlgorithmConfig getConfig();    // collects and returns the user’s input
}