package com.ea_framework.Views.VisualizeView;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Algorithms.TSPAlgorithm;
import javafx.scene.Node;

public interface VisualizeView {
    Node getView();
    void update(Object solution);
}
