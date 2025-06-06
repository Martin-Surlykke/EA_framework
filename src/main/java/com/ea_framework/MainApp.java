package com.ea_framework;

import com.ea_framework.Registries.RegistryInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainApp extends Application {

    public static void main(String[] args) {

        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader batchLoader = new FXMLLoader(getClass().getResource("/com/ea_framework/FrontPage.fxml"));
        Parent batchRoot = batchLoader.load();

        Scene scene = new Scene(batchRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("EA Framework");
        primaryStage.show();
    }

    public void init() {

        RegistryInitializer.initialize();
    }
}
