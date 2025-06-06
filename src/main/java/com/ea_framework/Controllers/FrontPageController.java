package com.ea_framework.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class FrontPageController {

    public Label createSchedule;
    public Label changeLocation;
    @FXML
    Pane minimize;

    private static File csvSaveDirectory = null;

    public static File getCsvSaveDirectory() {
        return csvSaveDirectory;
    }

    @FXML
    private void handleClose() {
        javafx.application.Platform.exit();
    }

    @FXML
    private void handleMinimize() {
        ((Stage) minimize.getScene().getWindow()).setIconified(true);
    }

    @FXML
    private void handleChangeLocation() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory to Save CSV Files");
        File selectedDir = directoryChooser.showDialog(changeLocation.getScene().getWindow());

        if (selectedDir != null) {
            csvSaveDirectory = selectedDir;
            changeLocation.setText(csvSaveDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void handleCreateSchedule() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ea_framework/batch.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) createSchedule.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
