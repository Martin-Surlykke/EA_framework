package com.ea_framework;

import com.ea_framework.Descriptors.OperatorDescriptor;
import com.ea_framework.Registries.OperatorRegistry;
import javafx.fxml.FXMLLoader;

public class OperatorRegistryInitializer {
    public static void initialize() {
        OperatorRegistry.register(OperatorType.FITNESS, "TSPDistance",
                new OperatorDescriptor("TSPDistance", () ->
                        FXMLLoader.load("/operator_config/TSPFitness.fxml", TSPFitnessController.class)
                ));

        OperatorRegistry.register(OperatorType.MUTATION, "TwoOpt",
                new OperatorDescriptor("TwoOpt", () ->
                        FXMLLoader.load("/operator_config/TwoOpt.fxml", TwoOptController.class)
                ));

        OperatorRegistry.register(OperatorType.CHOICE, "Greedy",
                new OperatorDescriptor("Greedy", () ->
                        FXMLLoader.load("/operator_config/GreedyChoice.fxml", GreedyChoiceController.class)
                ));
    }
}