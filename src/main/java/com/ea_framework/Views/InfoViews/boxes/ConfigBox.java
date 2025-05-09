package com.ea_framework.Views.InfoViews.boxes;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class ConfigBox {

    private final VBox box = new VBox(5);
    private final Map<String, Label> fields = new HashMap<>();

    public ConfigBox() {
        addField("Problem");
        addField("File");
        addField("Algorithm");
        addField("Choice Function");
        addField("Mutation Operator");
        addField("Fitness Function");
        addField("Start route");
    }

    public void addField(String title) {
        Label titleLabel = new Label(title + ": ");
        Label valueLabel = new Label("N/A");
        HBox hbox = new HBox(10, titleLabel, valueLabel);
        box.getChildren().add(hbox);
        fields.put(title, valueLabel);
    }

    public void updateField(String title, String value) {
        Label valueLabel = fields.get(title);
        if (valueLabel != null) {
            valueLabel.setText(value);
        }
    }

    public Node getView() {
        return box;
    }

}
