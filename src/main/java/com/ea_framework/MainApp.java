package com.ea_framework;

import com.ea_framework.Controller.BatchController;
import com.ea_framework.Controller.ScheduleController;
import com.ea_framework.MetaData.Registry;
import com.ea_framework.MetaData.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        // Initialize the registry
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ea_framework/Batch.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void init() {
        Registry.registerSearchSpace("GRAPH2D", new Graph2D());
        Registry.registerProblem("TSP", new TSP2DMetaData());
        Registry.registerAlgorithm("TSPGenericAlgorithm", new TSPGenericAlgorithm());

        RegistryInitializer.initialize();
    }
}
