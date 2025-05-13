package com.ea_framework.Views.VisualizeView;

import com.ea_framework.Problems.BitStringProblem;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class BitStringVisualizeView implements VisualizeView {

    private final Pane coordinatePane = new Pane();

    private final Pane templatePane = new Pane();
    private final Pane historyLayer = new Pane();
    private final Pane nodeLayer = new Pane();

    public BitStringVisualizeView(BitStringProblem problem) {
        coordinatePane.setPrefSize(700, 700);
        coordinatePane.getChildren().addAll(templatePane, historyLayer, nodeLayer);
        drawTemplate();
    }

    @Override
    public Node getView() {
        return coordinatePane;
    }

    @Override
    public void update(Object solution) {
        if (!(solution instanceof boolean[] bits)) {
            System.err.println("Invalid solution for BitStringVisualizeView");
            return;
        }
    }

    private void drawTemplate() {
    }
}
