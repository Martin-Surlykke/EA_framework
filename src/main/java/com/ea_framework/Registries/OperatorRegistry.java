package com.ea_framework.Registries;

import com.ea_framework.Descriptors.OperatorDescriptor;

import java.util.HashMap;
import java.util.Map;

public class OperatorRegistry {
    public static final Map<String, OperatorDescriptor> fitnessOperators = new HashMap<>();
    public static final Map<String, OperatorDescriptor> mutationOperators = new HashMap<>();
    public static final Map<String, OperatorDescriptor> choiceOperators = new HashMap<>();

    public static void initialize() {
        fitnessOperators.put("TSPDistance", new OperatorDescriptor("TSPDistance",
                () -> FXMLLoaderHelper.load("/operator_config/TSPFitness.fxml", TSPFitnessController.class)
        ));

        mutationOperators.put("TwoOpt", new OperatorDescriptor("TwoOpt",
                () -> FXMLLoaderHelper.load("/operator_config/TwoOpt.fxml", TwoOptController.class)
        ));

        choiceOperators.put("Greedy", new OperatorDescriptor("Greedy",
                () -> FXMLLoaderHelper.load("/operator_config/GreedyChoice.fxml", GreedyChoiceController.class)
        ));
    }
}