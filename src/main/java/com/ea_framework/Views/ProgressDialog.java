package com.ea_framework.Views;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProgressDialog {
    private final Stage dialogStage = new Stage();

    public ProgressDialog(String message) {
        Label label = new Label(message);
        VBox box = new VBox(label);
        box.setAlignment(Pos.CENTER);
        box.setPrefSize(200, 100);

        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setResizable(false);
        dialogStage.setScene(new Scene(box));
        dialogStage.setTitle("Please wait");
    }

    public void show(Stage owner) {
        dialogStage.initOwner(owner);
        dialogStage.show();
    }

    public void close() {
        dialogStage.close();
    }
}
