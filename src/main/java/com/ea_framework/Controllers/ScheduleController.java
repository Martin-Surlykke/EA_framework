package com.ea_framework.Controllers;

import com.ea_framework.Configs.BatchConfig;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ScheduleController {

    @FXML
    private VBox scheduleVBox;

    private final List<BatchConfig> savedBatches = new ArrayList<>();

    private Consumer<BatchConfig> onEditRequested;

    public void addBatch(BatchConfig config) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/BatchCard.fxml"));
            Node card = loader.load();

            BatchCardController controller = loader.getController();
            controller.setBatch(config);
            controller.setOnDelete(this::removeBatch); // callback
            controller.setOnEdit(this::editBatch);     // callback

            savedBatches.add(config);
            scheduleVBox.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editBatch(BatchConfig config) {
        if (onEditRequested != null) {
            onEditRequested.accept(config);
            removeBatch(config); // remove old version before editing
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
