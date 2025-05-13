package com.ea_framework.Views.VisualizeView;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Algorithms.TSPAlgorithm;
import com.ea_framework.Problems.BitStringProblem;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class BitStringView implements VisualizeView<boolean[]> {
    private final Pane coordinatePane = new Pane();

    private final Pane templatePane = new Pane();
    private final Pane historyLayer = new Pane();
    private final Pane nodeLayer = new Pane();

    public BitStringView(BitStringProblem problem) {
        coordinatePane.setPrefSize(700, 700);
        coordinatePane.getChildren().addAll(templatePane, historyLayer, nodeLayer);
        drawTemplate();
    }

    @Override
    public Node getView() {
        return coordinatePane;
    }

    @Override
    public void update(Algorithm<boolean[]> algorithm) {

    }

    private void drawTemplate() {

    }
}
