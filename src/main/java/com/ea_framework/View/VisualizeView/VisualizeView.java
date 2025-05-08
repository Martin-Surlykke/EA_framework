package com.ea_framework.View.VisualizeView;

import javafx.scene.Node;

public interface VisualizeView<T> {
    Node getView();
    void update(T problem);
}
