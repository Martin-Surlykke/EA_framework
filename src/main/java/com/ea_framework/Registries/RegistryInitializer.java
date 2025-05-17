package com.ea_framework.Registries;

import com.ea_framework.ACOTypes.MMASACOType;
import com.ea_framework.ACOTypes.StandardACOType;
import com.ea_framework.Algorithms.BitStringGenericAlgorithm;
import com.ea_framework.Algorithms.TSP2DACO;
import com.ea_framework.Algorithms.TSP2DGenericAlgorithm;
import com.ea_framework.Configs.BitStringGenericConfigPage;
import com.ea_framework.Configs.TSP2DACOConfig;
import com.ea_framework.Configs.TSP2DACOConfigPage;
import com.ea_framework.Controllers.OperatorControllers.OperatorConfigController;
import com.ea_framework.Descriptors.OperatorDescriptor;
import com.ea_framework.Filehandlers.BitStringFileHandler;
import com.ea_framework.Operators.ChoiceFunctions.BitStringGreedyChoice;
import com.ea_framework.Operators.ChoiceFunctions.BitStringSimulatedAnnealing;
import com.ea_framework.Operators.ChoiceFunctions.TSP2DGreedyChoice;
import com.ea_framework.Operators.ChoiceFunctions.TSP2DSimulatedAnnealing;
import com.ea_framework.Configs.TSP2DGenericConfigPage;
import com.ea_framework.Configurable;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Filehandlers.TSPFileHandler;
import com.ea_framework.OperatorType;
import com.ea_framework.Operators.FitnessFunctions.BitStringLeadingOnes;
import com.ea_framework.Operators.FitnessFunctions.BitStringOneMax;
import com.ea_framework.Operators.MutationFunctions.BitStringOneOneEA;
import com.ea_framework.Operators.MutationFunctions.BitStringRLS;
import com.ea_framework.SearchSpaces.BitStringSearchSpace;
import com.ea_framework.SearchSpaces.Graph2DSearchSpace;
import com.ea_framework.Operators.FitnessFunctions.TspEuclideanDistance;
import com.ea_framework.Operators.MutationFunctions.TSP2DTwoOpt;
import com.ea_framework.UIs.GenericOperatorUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.function.Supplier;

public class RegistryInitializer {

    public static void initialize() {

        SearchSpaceRegistry.register("Graph2D", Graph2DSearchSpace::new);
        SearchSpaceRegistry.register("BitString", BitStringSearchSpace::new);

        ProblemDescriptor tspProblemDescriptor = new ProblemDescriptor(
                "TSP2D",
                "Graph2D",
                new TSPFileHandler()
        );
        ProblemRegistry.register("TSP2D", tspProblemDescriptor);

        ProblemDescriptor bitStringProblemDescriptor = new ProblemDescriptor(
                "BitString",
                "BitString",
                new BitStringFileHandler()
        );
        ProblemRegistry.register("BitString", bitStringProblemDescriptor);


        // === Algorithm Descriptor ===
        AlgorithmDescriptor tspAlgorithmDescriptor = new AlgorithmDescriptor(
                TSP2DGenericAlgorithm.class,
                "TSP2DGeneric",
                "TSP2D",
                TSP2DGenericConfigPage::new
        );

        AlgorithmRegistry.register(tspAlgorithmDescriptor);

        AlgorithmDescriptor bitStringAlgorithmDescriptor = new AlgorithmDescriptor(
                BitStringGenericAlgorithm.class,
                "BitStringGeneric",
                "BitString",
                BitStringGenericConfigPage::new
        );
        AlgorithmRegistry.register(bitStringAlgorithmDescriptor);


        AlgorithmDescriptor tsp2DACODescriptor = new AlgorithmDescriptor(
                TSP2DACO.class,
                "TSP2DACO",
                "TSP2D",
                TSP2DACOConfigPage::new
        );
        AlgorithmRegistry.register(tsp2DACODescriptor);


        ACOTypeRegistry.register("Standard", new StandardACOType());

        ACOTypeRegistry.register("MMAS", new MMASACOType());

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
                "/com/ea_framework/operatorConfig/TSP2DGreedyChoice.fxml",
                TSP2DGreedyChoice::new
        );

        registerOperator(
                "TSP2DSimulatedAnnealing",
                OperatorType.CHOICE_TSP,
                "/com/ea_framework/operatorConfig/TSP2DSimulatedAnnealing.fxml",
                TSP2DSimulatedAnnealing::new
        );

        registerOperator(
                "BitStringRLS",
                OperatorType.MUTATION_BITSTRING,
                "/com/ea_framework/operatorConfig/BitStringRLS.fxml",
                BitStringRLS::new

        );

        registerOperator("BitString(1+1) EA",
                OperatorType.MUTATION_BITSTRING,
                "/com/ea_framework/operatorConfig/BitStringOneOneEA.fxml",
                BitStringOneOneEA::new
        );

        registerOperator(
                "BitStringOneMax",
                OperatorType.FITNESS_BITSTRING,
                "/com/ea_framework/operatorConfig/BitStringOneMax.fxml",
                BitStringOneMax::new
        );

        registerOperator(
                "BitStringLeadingOnes",
                OperatorType.FITNESS_BITSTRING,
                "/com/ea_framework/operatorConfig/BitStringLeadingOnes.fxml",
                BitStringLeadingOnes::new
        );

        registerOperator(
                "BitStringGreedyChoice",
                OperatorType.CHOICE_BITSTRING,
                "/com/ea_framework/operatorConfig/BitStringGreedyChoice.fxml",
                BitStringGreedyChoice::new
        );

        registerOperator("BitStringSimulatedAnnealing",
                OperatorType.CHOICE_BITSTRING,
                "/com/ea_framework/operatorConfig/BitStringSimulatedAnnealing.fxml",
                BitStringSimulatedAnnealing::new
        );
    }

    private static void registerOperator(
            String name,
            OperatorType type,
            String fxmlPath,
            Supplier<Configurable> operatorSupplier
    ) {
        OperatorDescriptor descriptor = new OperatorDescriptor(
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