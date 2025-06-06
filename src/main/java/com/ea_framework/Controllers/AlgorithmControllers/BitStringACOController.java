package com.ea_framework.Controllers.AlgorithmControllers;

import com.ea_framework.ACOTypes.BitStringACOType;
import com.ea_framework.ACOTypes.MMASBitStringACOType;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Configs.BitStringACOConfig;
import com.ea_framework.Problems.BitStringCompatible;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Registries.BitStringACOTypeRegistry;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

public class BitStringACOController implements AlgorithmConfigUI {

    // Controller class for the BitString ACO config page, handles user inputs
    @FXML private TextField numAntsField;
    @FXML private TextField evaporationRateField;
    @FXML private TextField initialPheromoneField;
    @FXML private TextField reinforcementField;
    @FXML private javafx.scene.control.ComboBox<String> acoTypeDropdown;

    private Runnable readyCallback = () -> {};

    // Initializes dropdowns and fields, allowing them to be handled by the user
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

    // Check if parameters are set in properly to allow going to the next tab
    private void checkReady() {
        if (isReadyToProceed() && readyCallback != null) {
            readyCallback.run();
        }
    }

    // Populates the config with the chosen parameters and operators
    @Override
    public AlgorithmConfig buildAlgorithmConfig(Problem problem) {
        if (!(problem instanceof BitStringCompatible bitStringProblem)) {
            throw new IllegalArgumentException("BitStringACOController can only be used with BitStringProblem");
        }

        Map<String, Object> raw = getConfigs(); // Collect all values from text fields

        String selected = acoTypeDropdown.getValue();
        BitStringACOType type;

        // Dynamically construct and populate MMAS type
        if (selected.equals("MMAS")) {
            type = new MMASBitStringACOType(); // No arguments
            ((MMASBitStringACOType) type).populate(raw); // Fill parameters from UI
        } else {
            // Fallback for other registered types (if any)
            type = BitStringACOTypeRegistry.get(selected);
            if (type == null) {
                throw new IllegalStateException("ACO type not registered: " + selected);
            }
        }

        BitStringACOConfig config = new BitStringACOConfig();
        raw.put("problemSize", bitStringProblem.getLength());
        raw.put("type", type);
        raw.put("typeName", selected);

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
}
