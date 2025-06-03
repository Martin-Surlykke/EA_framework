package com.ea_framework.Controllers.AlgorithmControllers;

import com.ea_framework.ACOTypes.BitStringACOType;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Configs.BitStringACOConfig;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Registries.BitStringACOTypeRegistry;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class BitStringACOController implements AlgorithmConfigUI {

    @FXML private TextField numAntsField;
    @FXML private TextField evaporationRateField;
    @FXML private TextField initialPheromoneField;
    @FXML private TextField reinforcementField;
    @FXML private javafx.scene.control.ComboBox<String> acoTypeDropdown;

    private Runnable readyCallback = () -> {};

    @FXML
    public void initialize() {
        // Populate ACO types
        acoTypeDropdown.getItems().addAll(BitStringACOTypeRegistry.getAllNames());
        if (!acoTypeDropdown.getItems().isEmpty()) {
            acoTypeDropdown.getSelectionModel().selectFirst();
        }

        numAntsField.setOnAction(e -> checkReady());
        evaporationRateField.setOnAction(e -> checkReady());
        initialPheromoneField.setOnAction(e -> checkReady());
        reinforcementField.setOnAction(e -> checkReady());

        numAntsField.focusedProperty().addListener((obs, old, focused) -> { if (!focused) checkReady(); });
        evaporationRateField.focusedProperty().addListener((obs, old, focused) -> { if (!focused) checkReady(); });
        initialPheromoneField.focusedProperty().addListener((obs, old, focused) -> { if (!focused) checkReady(); });
        reinforcementField.focusedProperty().addListener((obs, old, focused) -> { if (!focused) checkReady(); });
    }
    
    private void checkReady() {
        if (isReadyToProceed() && readyCallback != null) {
            readyCallback.run();
        }
    }

    @Override
    public AlgorithmConfig buildAlgorithmConfig(Problem problem) {
        Map<String, Object> raw = getConfigs();
        BitStringACOConfig config = new BitStringACOConfig();
        config.populate(raw, problem);
        return config;
    }

    @Override
    public Map<String, Object> getConfigs() {
        Map<String, Object> config = new HashMap<>();
        config.put("numAnts", Integer.parseInt(numAntsField.getText()));
        config.put("evaporationRate", Double.parseDouble(evaporationRateField.getText()));
        config.put("initialPheromone", Double.parseDouble(initialPheromoneField.getText()));
        config.put("reinforcement", Double.parseDouble(reinforcementField.getText()));

        String typeName = acoTypeDropdown.getValue();
        BitStringACOType selected = BitStringACOTypeRegistry.get(typeName);
        config.put("type", selected);

        return config;
    }

    @Override
    public boolean isReadyToProceed() {
        try {
            Integer.parseInt(numAntsField.getText());
            Double.parseDouble(evaporationRateField.getText());
            Double.parseDouble(initialPheromoneField.getText());
            Double.parseDouble(reinforcementField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void bindTo(Object config) {
        if (config instanceof BitStringACOConfig aco) {
            numAntsField.setText(String.valueOf(aco.numAnts()));
            evaporationRateField.setText(String.valueOf(aco.evaporationRate()));
            initialPheromoneField.setText(String.valueOf(aco.initialPheromone()));
            reinforcementField.setText(String.valueOf(aco.reinforcement()));
        }
    }

    @Override
    public void setOnReady(Runnable r) {
        this.readyCallback = r;
    }

    private double parseOrDefault(String text, double fallback) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
