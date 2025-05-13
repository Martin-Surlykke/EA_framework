package com.ea_framework.Views.VisualizeView;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Algorithms.TSPAlgorithm;
import javafx.scene.Node;

public interface VisualizeView<S> {
    Node getView();
    void update(Algorithm<S> problem);
}
