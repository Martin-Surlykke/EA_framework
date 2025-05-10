package com.ea_framework.Controllers.AlgorithmControllers;

import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Descriptors.OperatorDescriptor;
import com.ea_framework.UIs.GenericOperatorUI;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GenericAlgorithmController implements AlgorithmConfigUI {
    @FXML
    public Pane genericAlgorithmConfigPane;

    @FXML
    public ComboBox<String> fitnessDropDown;
    @FXML
    public ScrollPane fitnessScroll;
    @FXML
    public VBox fitnessConfigPane;

    @FXML
    public ComboBox<String> mutationDropDown;
    @FXML
    public ScrollPane mutationScroll;
    @FXML
    public VBox mutationConfigPane;

    @FXML
    public ComboBox<String> choiceDropDown;
    @FXML
    public ScrollPane choiceScroll;
    @FXML
    public VBox choiceConfigPane;

    private GenericOperatorUI fitnessConfigUI;
    private GenericOperatorUI mutationConfigUI;
    private GenericOperatorUI choiceConfigUI;

    private final Map<String, OperatorDescriptor> fitnessRegistry = new HashMap<>();
    private final Map<String, OperatorDescriptor> mutationRegistry = new HashMap<>();
    private final Map<String, OperatorDescriptor> choiceRegistry = new HashMap<>();


    @Override
    public Map<String, Object> getConfigs() {
        return Map.of();
    }
}
