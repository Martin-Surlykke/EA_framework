package com.ea_framework.Views.InfoViews;

import com.ea_framework.Views.InfoViews.boxes.StandardConfigBox;
import javafx.scene.Node;

public class ConfigurationView implements infoView<ConfigRecord> {

    private final StandardConfigBox standardConfigBox;

    public ConfigurationView() {
        this.standardConfigBox = new StandardConfigBox();
    }

    @Override
    public Node getView() {
        return standardConfigBox.getView();
    }

    @Override
    public void update(ConfigRecord record) {
        standardConfigBox.updateField("Problem", record.problem());
        standardConfigBox.updateField("File", record.file());
        standardConfigBox.updateField("Algorithm", record.algorithm());
        standardConfigBox.updateField("Choice Function", record.choiceFunction());
        standardConfigBox.updateField("Mutation Operator", record.mutationOperator());
        standardConfigBox.updateField("Fitness Function", record.fitnessFunction());
        standardConfigBox.updateField("Start route", record.startRoute());
    }
}
