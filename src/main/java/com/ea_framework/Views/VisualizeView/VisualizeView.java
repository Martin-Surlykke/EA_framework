package com.ea_framework.Views.VisualizeView;

import javafx.scene.Node;

public interface VisualizeView<T> {
    Node getView();
    void update(T problem);
}
