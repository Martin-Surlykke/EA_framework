package com.ea_framework.Controllers.AlgorithmControllers;

import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Configs.BitStringGenericAlgorithmConfig;
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

public class BitStringGenericAlgorithmController implements AlgorithmConfigUI {
     // Controller for the Generic Bitstring config page
    // Handles necessary dropdown, textfield etc. to allow the user to input chosen parameters and operators
    @FXML
    public Pane genericAlgorithmConfigPane;
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

    private GenericOperatorUI mutationConfigUI;
    private GenericOperatorUI choiceConfigUI;

    private final Map<String, OperatorDescriptor> fitnessRegistry = new HashMap<>();
    private final Map<String, OperatorDescriptor> mutationRegistry = new HashMap<>();
    private final Map<String, OperatorDescriptor> choiceRegistry = new HashMap<>();

    private AlgorithmConfigUI config;

    private Runnable onOperatorsFilled;

    // Operator types are defined to let the framework know which types of operators work for this algorithm
    private static final OperatorType[] OPERATOR_TYPES = {
            OperatorType.FITNESS_BITSTRING,
            OperatorType.MUTATION_BITSTRING,
            OperatorType.CHOICE_BITSTRING
    };


    // Checks if everything is filled out before allowing the user to move to the next tab
    private void checkAllOperatorsFilled() {
        boolean allFilled =
                        mutationConfigUI != null &&
                        choiceConfigUI != null &&
                        mutationConfigUI.getController().isFilled() &&
                        choiceConfigUI.getController().isFilled();

        if (allFilled && onOperatorsFilled != null) {
            onOperatorsFilled.run();
        }
    }


    // Handles changing config settings
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

        setupOperatorSelectionHandler(mutationDropDown, mutationConfigPane, mutationRegistry, op -> mutationConfigUI = op);
        setupOperatorSelectionHandler(choiceDropDown, choiceConfigPane, choiceRegistry, op -> choiceConfigUI = op);

        mutationDropDown.getItems().setAll(mutationRegistry.keySet());
        choiceDropDown.getItems().setAll(choiceRegistry.keySet());

        mutationDropDown.setDisable(false);
        choiceDropDown.setDisable(false);

        mutationScroll.setDisable(false);
        choiceScroll.setDisable(false);
    }

    // builds the config based on user-input
    @Override
    public AlgorithmConfig buildAlgorithmConfig(Problem problem) {
        Map<String, Object> operatorInstances = getConfigs();
        BitStringGenericAlgorithmConfig config = new BitStringGenericAlgorithmConfig();
        config.populate(operatorInstances, problem);
        return config;
    }


    // checks if the ui is filled out as necessary
    @Override
    public boolean isReadyToProceed() {
        return  mutationConfigUI != null &&
                choiceConfigUI != null &&
                mutationConfigUI.getController().isFilled() &&
                choiceConfigUI.getController().isFilled();
    }

    @Override
    public void setOnReady(Runnable callback) {
        this.onOperatorsFilled = callback;
        checkAllOperatorsFilled();
    }

}