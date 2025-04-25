package com.ea_framework.View.FitnessView;

import javafx.scene.Node;

public interface FitnessView {
    Node getView();

    void update(Number fitness, int iterations);
}
