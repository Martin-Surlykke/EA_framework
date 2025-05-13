package com.ea_framework.Views.InfoViews;

import com.ea_framework.Views.InfoViews.boxes.ConfigBox;
import javafx.scene.Node;

public class ConfigurationView implements infoView<ConfigRecord> {

    private final ConfigBox configBox;

    public ConfigurationView() {
        this.configBox = new ConfigBox();
    }

    @Override
    public Node getView() {
        return configBox.getView();
    }

    @Override
    public void update(ConfigRecord record) {
        configBox.updateField("Problem", record.problem());
        configBox.updateField("File", record.file());
        configBox.updateField("Algorithm", record.algorithm());
        configBox.updateField("Choice Function", record.choiceFunction());
        configBox.updateField("Mutation Operator", record.mutationOperator());
        configBox.updateField("Fitness Function", record.fitnessFunction());
        configBox.updateField("Start route", record.startRoute());
    }
}
