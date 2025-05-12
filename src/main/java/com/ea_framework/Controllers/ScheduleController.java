package com.ea_framework.Controllers;

import com.ea_framework.Configs.BatchConfig;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ScheduleController {

    @FXML
    private VBox scheduleVBox;

    private final List<BatchConfig> savedBatches = new ArrayList<>();

    private Consumer<BatchConfig> onEditRequested;

    public void addBatch(BatchConfig config) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ea_framework/BatchCard.fxml"));
            Node card = loader.load();  // Load FXML
            BatchCardController controller = loader.getController();  // After load()
            Objects.requireNonNull(controller, "BatchCardController was null");

            controller.setBatch(config);
            controller.setOnEdit(this::handleEdit);
            controller.setOnDelete(this::removeBatch);

            scheduleVBox.getChildren().add(card);
            savedBatches.add(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEdit(BatchConfig batchConfig) {
        if (onEditRequested != null) {
            onEditRequested.accept(batchConfig);
            removeBatch(batchConfig);
        }
    }

    public List<BatchConfig> getBatches() {
        return savedBatches;
    }

    public void removeBatch(BatchConfig config) {
        int index = savedBatches.indexOf(config);
        if (index >= 0) {
            savedBatches.remove(index);
            scheduleVBox.getChildren().remove(index);
        }
    }

    public void setOnEditRequested(Consumer<BatchConfig> callback) {
        this.onEditRequested = callback;
    }


}
