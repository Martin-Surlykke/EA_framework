package com.ea_framework.Configs;

import com.ea_framework.Views.ConfigViews.ConfigView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class BitStringGenericConfigPage implements ConfigView {
    private final Parent root;
    private final AlgorithmConfigUI controller;


    // Generic config page for bistring algorithsm
    // Connects the associated FXML file to the Config
    public BitStringGenericConfigPage() {
        try {
            URL fxmlURL = getClass().getResource("/com/ea_framework/Configs/BitStringGenericConfig.fxml");
            if (fxmlURL == null) {
                throw new IllegalStateException("FXML file not found for BitString config");
            }

            FXMLLoader loader = new FXMLLoader(fxmlURL);
            this.root = loader.load();
            this.controller = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load BitString config FXML", e);
        }
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public AlgorithmConfigUI getController() {
        return controller;
    }
}
