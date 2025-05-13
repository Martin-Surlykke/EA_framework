package com.ea_framework.Registries;

import com.ea_framework.Algorithms.TSPAlgorithm;
import com.ea_framework.Operators.ChoiceFunctions.TSP2DGreedyChoice;
import com.ea_framework.Operators.ChoiceFunctions.TSP2DSimulatedAnnealing;
import com.ea_framework.Configs.GenericConfigPage;
import com.ea_framework.Configs.TSP2DConfig;
import com.ea_framework.Configurable;
import com.ea_framework.Controllers.AlgorithmControllers.GenericAlgorithmController;
import com.ea_framework.Controllers.OperatorControllers.OperatorConfigController;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.OperatorDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Filehandlers.TSPFileHandler;
import com.ea_framework.OperatorType;
import com.ea_framework.Problems.TSP2DProblem;
import com.ea_framework.Runners.Runner;
import com.ea_framework.SearchSpaces.Graph2DSearchSpace;
import com.ea_framework.UIs.GenericOperatorUI;
import com.ea_framework.Operators.FitnessFunctions.TspEuclideanDistance;
import com.ea_framework.Operators.MutationFunctions.TSP2DTwoOpt;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.function.Supplier;

public class RegistryInitializer {

    public static void initialize() {

        SearchSpaceRegistry.register("Graph2D", Graph2DSearchSpace::new, int[].class);

        Runner<TSPAlgorithm> tspRunner = new Runner<>();

        ProblemDescriptor<int[], TSP2DProblem> tspProblemDescriptor = new ProblemDescriptor<>(
                "TSP2D",
                "Graph2D",
                new TSPFileHandler()
        );
        ProblemRegistry.register("TSP2D", tspProblemDescriptor);

        // === Algorithm Descriptor ===
        AlgorithmDescriptor<int[], TSP2DProblem, TSPAlgorithm, TSP2DConfig> tspAlgorithmDescriptor =
                new AlgorithmDescriptor<>(
                        TSPAlgorithm.class,
                        "TSPAlgorithm",
                        "TSP2D",
                        GenericConfigPage::new,
                        GenericAlgorithmController.class,
                        TSP2DConfig.class,
                        tspRunner,
                        OperatorType.FITNESS_TSP,
                        OperatorType.MUTATION_TSP,
                        OperatorType.CHOICE_TSP
                );
        AlgorithmRegistry.register(tspAlgorithmDescriptor);

        registerOperator(
                "TSP2DEuclideanDistance",
                OperatorType.FITNESS_TSP,
                "/com/ea_framework/operatorConfig/TSP2DEuclidianDistance.fxml",
                TspEuclideanDistance::new
        );

        registerOperator(
                "TSP2DTwoOpt",
                OperatorType.MUTATION_TSP,
                "/com/ea_framework/operatorConfig/TSP2DTwoOpt.fxml",
                TSP2DTwoOpt::new
        );

        registerOperator(
                "TSP2DGreedy",
                OperatorType.CHOICE_TSP,
                "/com/ea_framework/operatorConfig/GreedyTSP.fxml",
                TSP2DGreedyChoice::new
        );

        registerOperator(
                "TSP2DSimulatedAnnealing",
                OperatorType.CHOICE_TSP,
                "/com/ea_framework/operatorConfig/TSP2DSimulatedAnnealing.fxml",
                TSP2DSimulatedAnnealing::new
        );
    }

    private static <T extends Configurable<C>, C> void registerOperator(
            String name,
            OperatorType type,
            String fxmlPath,
            Supplier<T> operatorSupplier
    ) {
        OperatorDescriptor<T, C> descriptor = new OperatorDescriptor<>(
                name,
                type,
                fxmlPath,
                () -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(OperatorDescriptor.class.getResource(fxmlPath));
                        Parent root = loader.load();
                        OperatorConfigController controller = loader.getController();
                        return new GenericOperatorUI(root, controller);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                },
                operatorSupplier
        );
        OperatorRegistry.register(name, descriptor);
    }
}