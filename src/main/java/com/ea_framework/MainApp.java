package com.ea_framework;

import com.ea_framework.Controllers.BatchController;
import com.ea_framework.Controllers.ScheduleController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {

        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader scheduleLoader = new FXMLLoader(getClass().getResource("/com/ea_framework/ScheduleView.fxml"));
        Parent scheduleRoot = scheduleLoader.load();
        ScheduleController scheduleController = scheduleLoader.getController();

        FXMLLoader batchLoader = new FXMLLoader(getClass().getResource("/com/ea_framework/Batch.fxml"));
        Parent batchRoot = batchLoader.load();
        BatchController batchController = batchLoader.getController();

        batchController.setScheduleController(scheduleController);

        Scene scene = new Scene(batchRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("EA Framework");
        primaryStage.show();
    }

    public void init() {

        RegistryInitializer.initialize();
    }
}
