package com.ea_framework.Controllers.OperatorControllers;
import com.ea_framework.Operators.ChoiceFunctions.BitStringSimulatedAnnealing;
import com.ea_framework.Operators.ChoiceFunctions.TSP2DSimulatedAnnealing;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BitStringSimulatedAnnealingController implements OperatorConfigController {

    // Controller class for bitstring simulated annealing. An alpha value as well as initial temp
    // are made available to the user
    @FXML
    private TextField alphaValue;
    @FXML private TextField temperatureValue;
    private Runnable onChange;


    // returns the operator as an object with the necessary values filled out
    @Override
    public Object getOperator() {
        BitStringSimulatedAnnealing sa = new BitStringSimulatedAnnealing();
        sa.setAlpha(Double.parseDouble(alphaValue.getText()));
        sa.setT0(Double.parseDouble(temperatureValue.getText()));
        return sa;
    }

    @Override
    public boolean isFilled() {
        return !alphaValue.getText().isBlank() && !temperatureValue.getText().isBlank();
    }


    // Change listener to handle changing values
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