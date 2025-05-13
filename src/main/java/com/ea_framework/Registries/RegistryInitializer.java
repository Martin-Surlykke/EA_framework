package com.ea_framework.Registries;

import com.ea_framework.Algorithms.TSPAlgorithm;
import com.ea_framework.Configs.TSP2DConfig;
import com.ea_framework.Controllers.AlgorithmControllers.GenericAlgorithmController;
import com.ea_framework.Controllers.OperatorControllers.OperatorConfigController;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.OperatorDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Filehandlers.BitStringFileHandler;
import com.ea_framework.Filehandlers.TSPFileHandler;
import com.ea_framework.OperatorType;
import com.ea_framework.Runners.BitStringRunner;
import com.ea_framework.Runners.TSP2DRunner;
import com.ea_framework.SearchSpaces.BitStringSearchSpace;
import com.ea_framework.SearchSpaces.Graph2DSearchSpace;
import com.ea_framework.UIs.GenericOperatorUI;
import com.ea_framework.Configs.GenericConfigPage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class RegistryInitializer {

    public static void initialize() {

        // Register Search Spaces
        SearchSpaceRegistry.register(
                "BitString",
                BitStringSearchSpace::new
        );

        SearchSpaceRegistry.register(
                "Graph2D",
                Graph2DSearchSpace::new
        );


        // Register Problems
        ProblemRegistry.register(
                new ProblemDescriptor(
                        "BitStringProblem",
                        "BitString",
                        new BitStringFileHandler(),
                        new BitStringRunner()
                )
        );

        ProblemRegistry.register(
                new ProblemDescriptor(
                        "TSP2D",
                        "Graph2D",
                        new TSPFileHandler()
                )
        );

        // Register Algorithms
        AlgorithmRegistry.register(
                new AlgorithmDescriptor<>(
                        TSPAlgorithm.class,
                        "TSPAlgorithm",
                        "TSP2D",
                        GenericConfigPage::new,
                        GenericAlgorithmController.class,
                        TSP2DConfig.class,
                        new TSP2DRunner(),
                        OperatorType.FITNESS_TSP,
                        OperatorType.MUTATION_TSP,
                        OperatorType.CHOICE_TSP
                )
        );

        // Register Operators
        OperatorRegistry.register("RLS", new OperatorDescriptor(
                "RLS",
                OperatorType.MUTATION_BITSTRING,
                "/com/ea_framework/operatorConfig/RLS.fxml",
                () -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                OperatorDescriptor.class.getResource("/com/ea_framework/operatorConfig/RLS.fxml")
                        );
                        Parent root = loader.load();
                        OperatorConfigController controller = loader.getController();
                        return new GenericOperatorUI(root, controller);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ));

        OperatorRegistry.register("SimulatedAnnealing", new OperatorDescriptor(
                "SimulatedAnnealing",
                OperatorType.CHOICE_TSP,
                "/com/ea_framework/operatorConfig/TSP2DSimulatedAnnealing.fxml",
                () -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                OperatorDescriptor.class.getResource("/com/ea_framework/operatorConfig/TSP2DSimulatedAnnealing.fxml")
                        );
                        Parent root = loader.load();
                        OperatorConfigController controller = loader.getController();
                        return new GenericOperatorUI(root, controller);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ));

        OperatorRegistry.register("Greedy", new OperatorDescriptor(
                "Greedy",
                OperatorType.CHOICE_TSP,
                "/com/ea_framework/operatorConfig/GreedyTSP.fxml",
                () -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                OperatorDescriptor.class.getResource("/com/ea_framework/operatorConfig/GreedyTSP.fxml")
                        );
                        Parent root = loader.load();
                        OperatorConfigController controller = loader.getController();
                        return new GenericOperatorUI(root, controller);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ));

        OperatorRegistry.register("TwoOpt", new OperatorDescriptor(
                "TwoOpt",
                OperatorType.MUTATION_TSP,
                "/com/ea_framework/operatorConfig/TwoOptTSP.fxml",
                () -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                OperatorDescriptor.class.getResource("/com/ea_framework/operatorConfig/TwoOptTSP.fxml")
                        );
                        Parent root = loader.load();
                        OperatorConfigController controller = loader.getController();
                        return new GenericOperatorUI(root, controller);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ));

        OperatorRegistry.register("EuclidianDistance", new OperatorDescriptor(
                "EuclidianDistance",
                OperatorType.FITNESS_TSP,
                "/com/ea_framework/operatorConfig/TSP2DEuclidianDistance.fxml",
                () -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(
                                OperatorDescriptor.class.getResource("/com/ea_framework/operatorConfig/TSP2DEuclidianDistance.fxml")
                        );
                        Parent root = loader.load();
                        OperatorConfigController controller = loader.getController();
                        return new GenericOperatorUI(root, controller);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ));


    }
}
