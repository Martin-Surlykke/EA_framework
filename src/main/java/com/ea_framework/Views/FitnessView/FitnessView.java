package com.ea_framework.Views.FitnessView;

import javafx.scene.Node;

public interface FitnessView {
    Node getView();

    void update(Number fitness, int iterations);
}
