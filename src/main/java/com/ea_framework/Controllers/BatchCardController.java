package com.ea_framework.Controllers;

import com.ea_framework.Configs.BatchConfig;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.function.Consumer;

public class BatchCardController {

    // Controller for "Batch cards" the display item used to show what has been added to a current schedule

    @FXML
    private Label titleLabel;

    @FXML
    private Label summaryLabel;

    private BatchConfig batchConfig;

    private Consumer<BatchConfig> onDelete;
    private Consumer<BatchConfig> onEdit;

    @FXML
    public void initialize() {
        assert titleLabel != null : "titleLabel was not injected!";
        assert summaryLabel != null : "summaryLabel was not injected!";
    }

    public void setBatch(BatchConfig batchConfig) {
                             this.batchConfig = batchConfig;
        titleLabel.setText(batchConfig.getAlgorithmName() + " + " + batchConfig.getProblemName());
        summaryLabel.setText("SearchSpace: " + batchConfig.getSearchSpaceName() + ", Repeats: " + batchConfig.getRepeats() + ", Termination: " + batchConfig.getTermination());
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

    public void setTitle(String helloWorld) {
        titleLabel.setText(helloWorld);
    }

    public void setSummary(String thisIsASummary) {
        summaryLabel.setText(thisIsASummary);
    }
}
