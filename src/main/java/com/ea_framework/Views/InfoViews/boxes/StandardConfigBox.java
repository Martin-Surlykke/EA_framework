package com.ea_framework.Views.InfoViews.boxes;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import java.util.HashMap;
import java.util.Map;

public class StandardConfigBox {

    private final VBox root;
    private final GridPane grid;
    private final Map<String, Label> valueLabels;

    public StandardConfigBox() {
        root = new VBox();
        grid = new GridPane();
        valueLabels = new HashMap<>();

        // Configure column layout: fixed width for keys, flexible for values
        ColumnConstraints keyCol = new ColumnConstraints();
        keyCol.setMinWidth(150);
        keyCol.setMaxWidth(250);
        keyCol.setPrefWidth(220);

        ColumnConstraints valueCol = new ColumnConstraints();
        valueCol.setHgrow(Priority.ALWAYS); // Let value column grow with window

        grid.getColumnConstraints().addAll(keyCol, valueCol);

        // Add fields in the correct order with proper spelling
        addField("Search space");
        addField("Problem");
        addField("File");
        addField("Algorithm");
        addField("Termination Condition");

        root.getChildren().add(grid);
    }

    private void addField(String label) {
        int row = grid.getRowCount();
        Label keyLabel = new Label(label + ":");

        Label valueLabel = new Label("N/A");
        valueLabel.setWrapText(true);
        valueLabel.setMaxWidth(Double.MAX_VALUE);
        valueLabel.setPrefWidth(400);

        GridPane.setHgrow(valueLabel, Priority.ALWAYS);

        grid.addRow(row, keyLabel, valueLabel);
        valueLabels.put(label, valueLabel);
    }

    public void updateField(String label, String value) {
        Label valueLabel = valueLabels.get(label);
        if (valueLabel != null) {
            valueLabel.setText(value);
        }
    }

    public VBox getView() {
        return root;
    }
}
