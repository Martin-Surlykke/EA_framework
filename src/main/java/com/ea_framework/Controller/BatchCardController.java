package com.ea_framework.Controller;

import com.ea_framework.Configs.BatchConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.function.Consumer;

public class BatchCardController {

    @FXML
    private Label titleLabel;

    @FXML
    private Label summaryLabel;

    private BatchConfig batchConfig;

    private Consumer<BatchConfig> onDelete;
    private Consumer<BatchConfig> onEdit;

    public void setBatchCard(BatchConfig batchConfig) {
                             this.batchConfig = batchConfig;
        titleLabel.setText(batchConfig.getAlgorithm() + " + " + batchConfig.getProblem());
        summaryLabel.setText("SearchSpace: " + batchConfig.getSearchSpace() + ", Repeats: " + batchConfig.getRepeats());
    }


    public void setOnEdit(Consumer<BatchConfig> callback) {
        this.onEdit = callback;
    }

    public void setOnDelete(Consumer<BatchConfig> callback) {
        this.onDelete = callback;
    }

    @FXML
    private void onEdit() {
        if (onEdit != null) {
            onEdit.accept(batchConfig);
        }
    }

    @FXML
    private void onDelete() {
        if (onDelete != null) {
            onDelete.accept(batchConfig);
        }
    }

    public void setBatch(BatchConfig config) {
    }
}
