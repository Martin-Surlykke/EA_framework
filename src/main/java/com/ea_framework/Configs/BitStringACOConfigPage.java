package com.ea_framework.Configs;

import com.ea_framework.Views.ConfigViews.ConfigView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class BitStringACOConfigPage implements ConfigView {
    private final Parent root;
    private final AlgorithmConfigUI controller;

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

    @Override
    public Parent getRoot() {
        return root;
    }

    @Override
    public AlgorithmConfigUI getController() {
        return controller;
    }
}
