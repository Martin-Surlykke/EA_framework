package com.ea_framework.Views.InfoViews;

import com.ea_framework.Views.InfoViews.boxes.StandardStatBox;
import javafx.scene.Node;

public class StatView implements infoView<StatRecord>{
    private final StandardStatBox standardStatBox;

    public StatView() {
        this.standardStatBox = new StandardStatBox();
    }
    @Override
    public Node getView() {
        return standardStatBox.getView();
    }

    @Override
    public void update(StatRecord record) {
        if (record == null) {
            System.err.println("StatView: Tried to update with null record");
            return;
        }
        standardStatBox.updateField("Iterations", String.valueOf(record.iteration()));
        standardStatBox.updateField("Evaluations", String.valueOf(record.evaluations()));
        standardStatBox.updateField("Fitness", String.valueOf(record.fitness()));

        standardStatBox.updateField("Best iteration", String.valueOf(record.bestIteration()));
        standardStatBox.updateField("Best evaluation", String.valueOf(record.bestEvaluation()));
        standardStatBox.updateField("Best fitness", String.valueOf(record.bestFitness()));

    }
}
