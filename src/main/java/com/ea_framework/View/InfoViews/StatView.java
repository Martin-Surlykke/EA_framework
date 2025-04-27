package com.ea_framework.View.InfoViews;

import com.ea_framework.View.InfoViews.boxes.StatBox;
import javafx.scene.Node;

public class StatView implements infoView<StatRecord>{
    private final StatBox statBox;

    public StatView() {
        this.statBox = new StatBox();
    }
    @Override
    public Node getView() {
        return statBox.getView();
    }

    @Override
    public void update(StatRecord record) {
        statBox.updateField("Iterations", String.valueOf(record.iteration()));
        statBox.updateField("Evaluations", String.valueOf(record.evaluations()));
        statBox.updateField("Fitness", String.valueOf(record.fitness()));

        statBox.updateField("Best iteration", String.valueOf(record.bestIteration()));
        statBox.updateField("Best evaluation", String.valueOf(record.bestEvaluation()));
        statBox.updateField("Best fitness", String.valueOf(record.bestFitness()));

    }
}
