package com.ea_framework.Controllers.AlgorithmControllers;

import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Configs.TSP2DGenericAlgorithmConfig;
import com.ea_framework.Descriptors.OperatorDescriptor;
import com.ea_framework.OperatorType;
import com.ea_framework.Problems.Problem;
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

public class TSP2DGenericAlgorithmController implements AlgorithmConfigUI {

    // Controller class for the generic TSP algorithm config page
    @FXML
    public Pane genericAlgorithmConfigPane;

    // Boxes textfields etc. for inputting data chosen parameters and operators
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


    // Operator type is defined to tell the framework which operator types are allowed for this algorithm
    private static final OperatorType[] OPERATOR_TYPES = {
            OperatorType.FITNESS_TSP,
            OperatorType.MUTATION_TSP,
            OperatorType.CHOICE_TSP
    };

    public void setOnOperatorsFilled(Runnable action) {
        this.onOperatorsFilled = action;
    }


    // Checks if all data is filled in as necessary
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


    // Handles changes in the configuration
    private void onConfigChanged(GenericOperatorUI ui, VBox configPane, Consumer<GenericOperatorUI> setUI) {
        configPane.getChildren().setAll(ui.getRoot());
        setUI.accept(ui);
        ui.getController().setChangeListener(this::checkAllOperatorsFilled);
        checkAllOperatorsFilled();
    }


    // generalized method for handling displaying the possible operators to the user
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

    // Returns a hashmap with an identifying string, and the operator object
    @Override
    public Map<String, Object> getConfigs() {
        Map<String, Object> map = new HashMap<>();

        if (fitnessConfigUI != null)
            map.put("fitness", fitnessConfigUI.getController().getOperator());

        if (mutationConfigUI != null)
            map.put("mutation", mutationConfigUI.getController().getOperator());

        if (choiceConfigUI != null)
            map.put("choice", choiceConfigUI.getController().getOperator());

        return map;
    }



    @Override
    public void bindTo(Object config) {
        this.config = (AlgorithmConfigUI) config;

        OperatorType[] types = OPERATOR_TYPES;

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


    // Builds the algorithm config based on the given inputs
    @Override
    public AlgorithmConfig buildAlgorithmConfig(Problem problem) {
        Map<String, Object> operatorInstances = getConfigs();
        TSP2DGenericAlgorithmConfig config = new TSP2DGenericAlgorithmConfig();
        config.populate(operatorInstances, problem);
        return config;
    }


    // checks if things are filled out and the operators have been collecetd
    @Override
    public boolean isReadyToProceed() {
        return fitnessConfigUI != null &&
                mutationConfigUI != null &&
                choiceConfigUI != null &&
                fitnessConfigUI.getController().isFilled() &&
                mutationConfigUI.getController().isFilled() &&
                choiceConfigUI.getController().isFilled();
    }

    @Override
    public void setOnReady(Runnable callback) {
        this.onOperatorsFilled = callback;
        checkAllOperatorsFilled();
    }

}