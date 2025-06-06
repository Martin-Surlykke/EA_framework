package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.ChoiceFunctions.TSP2DSimulatedAnnealing;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TSP2DSimulatedAnnealingController implements OperatorConfigController {

    // Config controller for simulated annealing for TSP problems
    // Handles the addition of alpha, and start temperature
    @FXML
    private TextField alphaValue;
    @FXML private TextField temperatureValue;
    private Runnable onChange;

    // the object is returned with necessary parameters defined
    @Override
    public Object getOperator() {
        TSP2DSimulatedAnnealing sa = new TSP2DSimulatedAnnealing();
        sa.setAlpha(Double.parseDouble(alphaValue.getText()));
        sa.setT0(Double.parseDouble(temperatureValue.getText()));
        return sa;
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