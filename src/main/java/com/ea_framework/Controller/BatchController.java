package com.ea_framework.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class BatchController {

    @FXML
    private Label addBatch;

    @FXML
    private VBox scrollVBox;

    @FXML
    public void initialize() {
        addBatch.setOnMouseClicked(event -> {
            System.out.println("Clicked!");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ea_framework/Batch_setup.fxml"));
                Region batchNode = loader.load();
                VBox.setVgrow(batchNode, Priority.NEVER);
                batchNode.prefWidthProperty().bind(scrollVBox.widthProperty());
                scrollVBox.getChildren().add(batchNode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}