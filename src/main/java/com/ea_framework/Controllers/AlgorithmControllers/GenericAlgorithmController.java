package com.ea_framework.Controllers.AlgorithmControllers;

import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.OperatorDescriptor;
import com.ea_framework.GenericOperatorUI;
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

    private void loadOperatorUI(String key, Map<String, OperatorDescriptor> registry, VBox target, Consumer<GenericOperatorUI> setter) {
        target.getChildren().clear();
        OperatorDescriptor descriptor = registry.get(key);
        if (descriptor == null) return;

        GenericOperatorUI ui = descriptor.getConfigUI();
        target.getChildren().add(ui.getRoot());
        setter.accept(ui);
    }

    public Map<String, Object> getCombinedConfigs() {
        Map<String, Object> config = new HashMap<>();
        config.put("fitness", fitnessDropDown.getValue());
        config.put("mutation", mutationDropDown.getValue());
        config.put("choice", choiceDropDown.getValue());

        if (fitnessConfigUI != null)
            config.put("fitness_config", fitnessConfigUI.getConfigs());
        if (mutationConfigUI != null)
            config.put("mutation_config", mutationConfigUI.getConfigs());
        if (choiceConfigUI != null)
            config.put("choice_config", choiceConfigUI.getConfigs());

        return config;
    }

    @Override
    public Map<String, Object> getConfigs() {
        return getCombinedConfigs();
    }
}
