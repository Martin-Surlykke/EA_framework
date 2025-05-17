package com.ea_framework.Controllers.AlgorithmControllers;

import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Problems.TSP2DProblem;
import com.ea_framework.Registries.ACOTypeRegistry;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Map;


import com.ea_framework.Configs.TSP2DACOConfig;
import com.ea_framework.ACOTypes.ACOType;
import javafx.fxml.FXML;
import java.util.HashMap;

public class TSP2DACOController implements AlgorithmConfigUI {

    private AlgorithmConfig config;
    @FXML private TextField alphaField;
    @FXML private TextField betaField;
    @FXML private ComboBox<String> type;

    private Runnable readyCallback;

    @FXML
    public void initialize() {
        type.getItems().addAll("Standard", "MMAS");
        type.setValue("Standard");
        alphaField.textProperty().addListener((obs, oldVal, newVal) -> checkReady());
        betaField.textProperty().addListener((obs, oldVal, newVal) -> checkReady());
        type.valueProperty().addListener((obs, oldVal, newVal) -> checkReady());
    }

    @Override
    public void bindTo(Object config) {

    }

    @Override
    public void setOnReady(Runnable r) {
        this.readyCallback = r;
    }

    @Override
    public Map<String, Object> getConfigs() {
        Map<String, Object> map = new HashMap<>();
        double alpha = parseOrDefault(alphaField.getText(), 1.5);
        double beta = parseOrDefault(betaField.getText(), 2.5);

        String selectedName = type.getValue();
        ACOType strategy = ACOTypeRegistry.get(selectedName);

        if (strategy == null) {
            throw new IllegalStateException("ACOType not registered: " + selectedName);
        }

        map.put("alpha", alpha);
        map.put("beta", beta);
        map.put("type", strategy);
        map.put("typeName", selectedName);
        return map;
    }

    @Override
    public AlgorithmConfig buildAlgorithmConfig(Problem problem) {
        if (!(problem instanceof TSP2DProblem tspProblem)) {
            throw new IllegalArgumentException("TSP2DACOController can only be used with TSP2DProblem");
        }
        double[][] matrix = tspProblem.getDistanceMatrix();
        int[] initialTour = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) initialTour[i] = i;

        TSP2DACOConfig config = new TSP2DACOConfig();
        Map<String, Object> raw = getConfigs();
        raw.put("distanceMatrix", matrix);
        raw.put("initialTour", initialTour);

        config.populate(raw, problem);
        return config;
    }

    @Override
    public boolean isReadyToProceed() {
        return (!alphaField.getText().isEmpty() &&
                !betaField.getText().isEmpty() &&
                type != null);
    }

    private void checkReady() {
        if (isReadyToProceed() && readyCallback != null) {
            readyCallback.run();
        }
    }

    private double parseOrDefault(String text, double def) {
        try {
            return Double.parseDouble(text);
        } catch (Exception e) {
            return def;
        }
    }
}

