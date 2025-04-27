package com.ea_framework.View.CandidateView;

import com.ea_framework.Candidates.bitStringCandidate;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class BitStringView implements CandidateView<bitStringCandidate> {
    private final Pane coordinatePane = new Pane();

    private final Pane templatePane = new Pane();
    private final Pane historyLayer = new Pane();
    private final Pane nodeLayer = new Pane();

    public BitStringView(bitStringCandidate bitStringCandidate) {
        coordinatePane.setPrefSize(700, 700);
        coordinatePane.getChildren().addAll(templatePane, historyLayer, nodeLayer);
        drawTemplate();
    }

    @Override
    public Node getView() {
        return coordinatePane;
    }

    @Override
    public void update(bitStringCandidate candidate) {

    }

    private void drawTemplate() {

    }
}
