package com.ea_framework.Configs;

import com.ea_framework.Views.ConfigViews.ConfigView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;


public class BitStringACOConfigPage implements ConfigView {
    // This class represents a configuration page for the Bit String ACO algorithm.
// It loads the FXML layout and provides access to the controller for configuration settings.
    private final Parent root;

    // The controller for the Bit String ACO configuration UI.
    private final AlgorithmConfigUI controller;

    // Constructor that initializes the BitStringACOConfigPage by loading the FXML file.
    public BitStringACOConfigPage() {
        try {
            URL fxmlURL = getClass().getResource("/com/ea_framework/Configs/BitStringACOConfig.fxml");
            if (fxmlURL == null) {
                throw new IllegalStateException("FXML file not found at expected path");
            }
            System.out.println("Resolved FXML URL: " + fxmlURL);

            FXMLLoader loader = new FXMLLoader(fxmlURL);
            this.root = loader.load();
            this.controller = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load BitStringACOConfig.fxml", e);
        }
    }

    // Returns the root Parent node of the configuration UI, which is used in the JavaFX scene graph.

    @Override
    public Parent getRoot() {
        return root;
    }

    // Returns the controller for the Bit String ACO configuration UI, which allows access to the configuration settings.
    @Override
    public AlgorithmConfigUI getController() {
        return controller;
    }
}
