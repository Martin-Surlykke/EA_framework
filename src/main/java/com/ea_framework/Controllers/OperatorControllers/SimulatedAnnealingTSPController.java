package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.ChoiceFunctions.TSP2DSimulatedAnnealing;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SimulatedAnnealingTSPController implements OperatorConfigController {
    @FXML
    private TextField alphaValue;
    @FXML private TextField temperatureValue;
    private Runnable onChange;

    @Override
    public Object getConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("alpha", Double.parseDouble(alphaValue.getText()));
        config.put("t0", Double.parseDouble(temperatureValue.getText()));
        return config;
    }

    @Override
    public boolean isFilled() {
        return !alphaValue.getText().isBlank() && !temperatureValue.getText().isBlank();
    }

    @Override
    public void setChangeListener(Runnable onChange) {
        this.onChange = onChange;

        alphaValue.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
            if (!newFocus && this.onChange != null) {
                this.onChange.run();
            }
        });

        temperatureValue.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
            if (!newFocus && this.onChange != null) {
                this.onChange.run();
            }
        });

        alphaValue.setOnAction(e -> {
            if (this.onChange != null) {
                this.onChange.run();
            }
        });

        temperatureValue.setOnAction(e -> {
            if (this.onChange != null) {
                this.onChange.run();
            }
        });
    }
}