package com.ea_framework.Controllers.OperatorControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;

public class RLSController implements OperatorConfigController {
    @FXML
    private Slider probabilitySlider;

    public Double getConfig() {
        return probabilitySlider.getValue();
    }
}