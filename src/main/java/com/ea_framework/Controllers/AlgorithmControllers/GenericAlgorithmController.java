package com.ea_framework.Controllers.AlgorithmControllers;

import com.ea_framework.Operators.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Configs.TSP2DConfig;
import com.ea_framework.Descriptors.OperatorDescriptor;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Operators.MutationFunctions.MutationOperator;
import com.ea_framework.OperatorType;
import com.ea_framework.UIs.GenericOperatorUI;
import com.ea_framework.Registries.OperatorRegistry;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
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

    private AlgorithmConfigUI config;

    private Runnable onOperatorsFilled;

    public void setOnOperatorsFilled(Runnable action) {
        this.onOperatorsFilled = action;
    }

    private void checkAllOperatorsFilled() {
        boolean allFilled =
                fitnessConfigUI != null &&
                        mutationConfigUI != null &&
                        choiceConfigUI != null &&
                        fitnessConfigUI.getController().isFilled() &&
                        mutationConfigUI.getController().isFilled() &&
                        choiceConfigUI.getController().isFilled();

        if (allFilled && onOperatorsFilled != null) {
            onOperatorsFilled.run();
        }
    }

    private void onConfigChanged(GenericOperatorUI ui, VBox configPane, Consumer<GenericOperatorUI> setUI) {
        configPane.getChildren().setAll(ui.getRoot());
        setUI.accept(ui);
        ui.getController().setChangeListener(this::checkAllOperatorsFilled);
        checkAllOperatorsFilled();
    }

    private void setupOperatorSelectionHandler(
            ComboBox<String> dropDown,
            VBox configPane,
            Map<String, OperatorDescriptor> localRegistry,
            Consumer<GenericOperatorUI> setConfigUI
    ) {
        dropDown.setOnAction(e -> {
            String selected = dropDown.getValue();
            OperatorDescriptor descriptor = localRegistry.get(selected);
            if (descriptor != null) {
                try {
                    GenericOperatorUI ui = descriptor.loadUI();
                    onConfigChanged(ui, configPane, setConfigUI);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public Map<String, Object> getConfigs() {
        Map<String, Object> map = new HashMap<>();

        // Pull from controllers
        Fitness<int[], Double> fitness = (Fitness<int[], Double>) fitnessConfigUI.getController().getConfig();
        MutationOperator<int[]> mutation = (MutationOperator<int[]>) mutationConfigUI.getController().getConfig();
        ChoiceFunction<int[], Double> choice = (ChoiceFunction<int[], Double>) choiceConfigUI.getController().getConfig();

        // Put them into the map with keys that match what TSP2DConfig expects
        map.put("fitness", fitness);
        map.put("mutation", mutation);
        map.put("choice", choice);

        // Only now is the config safe to construct
        AlgorithmConfig config = new TSP2DConfig() {
        };
        config.populate(map);

        return map;
    }

    @Override
    public OperatorType[] getOperatorTypes() {
        return new OperatorType[] {
                OperatorType.FITNESS_TSP,
                OperatorType.MUTATION_TSP,
                OperatorType.CHOICE_TSP
        };
    }

    @Override
    public void bindTo(Object config) {

        this.config = (AlgorithmConfigUI) config;

        OperatorType[] types = getOperatorTypes();
        if (types.length != 3) {
            throw new IllegalStateException("GenericAlgorithmController expects 3 operator types.");
        }

        fitnessRegistry.putAll(OperatorRegistry.getRegistryByType(types[0]));
        mutationRegistry.putAll(OperatorRegistry.getRegistryByType(types[1]));
        choiceRegistry.putAll(OperatorRegistry.getRegistryByType(types[2]));

        setupOperatorSelectionHandler(fitnessDropDown, fitnessConfigPane, fitnessRegistry, op -> fitnessConfigUI = op);
        setupOperatorSelectionHandler(mutationDropDown, mutationConfigPane, mutationRegistry, op -> mutationConfigUI = op);
        setupOperatorSelectionHandler(choiceDropDown, choiceConfigPane, choiceRegistry, op -> choiceConfigUI = op);

        fitnessDropDown.getItems().setAll(fitnessRegistry.keySet());
        mutationDropDown.getItems().setAll(mutationRegistry.keySet());
        choiceDropDown.getItems().setAll(choiceRegistry.keySet());

        fitnessDropDown.setDisable(false);
        mutationDropDown.setDisable(false);
        choiceDropDown.setDisable(false);

        fitnessScroll.setDisable(false);
        mutationScroll.setDisable(false);
        choiceScroll.setDisable(false);
    }


}