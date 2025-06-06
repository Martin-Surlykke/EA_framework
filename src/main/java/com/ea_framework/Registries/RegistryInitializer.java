package com.ea_framework.Registries;

import com.ea_framework.ACOTypes.MMASACOType;
import com.ea_framework.ACOTypes.StandardACOType;
import com.ea_framework.Algorithms.BitStringACO;
import com.ea_framework.Algorithms.BitStringGenericAlgorithm;
import com.ea_framework.Algorithms.TSP2DACO;
import com.ea_framework.Algorithms.TSP2DGenericAlgorithm;
import com.ea_framework.Configs.BitStringGenericConfigPage;
import com.ea_framework.Configs.TSP2DACOConfigPage;
import com.ea_framework.Controllers.OperatorControllers.OperatorConfigController;
import com.ea_framework.Descriptors.OperatorDescriptor;
import com.ea_framework.Descriptors.TerminationDescriptor;
import com.ea_framework.Filehandlers.BitStringLeadingOnesFileHandler;
import com.ea_framework.Filehandlers.BitStringOneMaxFileHandler;
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
import com.ea_framework.Operators.MutationFunctions.TSP2D_One_One_EA;
import com.ea_framework.SearchSpaces.BitStringSearchSpace;
import com.ea_framework.SearchSpaces.Graph2DSearchSpace;
import com.ea_framework.Operators.FitnessFunctions.TspEuclideanDistance;
import com.ea_framework.Operators.MutationFunctions.TSP2DTwoOpt;
import com.ea_framework.Termination.FitnessThresholdCondition;
import com.ea_framework.Termination.MaxIterationsCondition;
import com.ea_framework.Termination.TerminationCondition;
import com.ea_framework.UIs.GenericOperatorUI;

import com.ea_framework.ACOTypes.StandardBitStringACOType;
import com.ea_framework.ACOTypes.MMASBitStringACOType;
import com.ea_framework.Configs.BitStringACOConfigPage;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.function.Supplier;

public class RegistryInitializer {

    public static void initialize() {

        SearchSpaceRegistry.register("Permutation", Graph2DSearchSpace::new);
        SearchSpaceRegistry.register("BitString", BitStringSearchSpace::new);

        ProblemDescriptor tspProblemDescriptor = new ProblemDescriptor(
                "TSP2D",
                "Permutation",
                new TSPFileHandler()
        );
        ProblemRegistry.register("TSP2D", tspProblemDescriptor);

        ProblemDescriptor bitStringOneMaxProblemDescriptor = new ProblemDescriptor(
                "BitStringOneMax",
                "BitString",
                new BitStringOneMaxFileHandler()
        );
        ProblemRegistry.register("BitStringOneMax", bitStringOneMaxProblemDescriptor);

        ProblemDescriptor bitStringLeadingOnesProblemDescriptor = new ProblemDescriptor(
                "BitStringLeadingOnes",
                "BitString",
                new BitStringLeadingOnesFileHandler()
        );
        ProblemRegistry.register("BitStringLeadingOnes", bitStringLeadingOnesProblemDescriptor);

        // === Algorithm Descriptor ===
        AlgorithmDescriptor tspAlgorithmDescriptor = new AlgorithmDescriptor(
                TSP2DGenericAlgorithm.class,
                "TSP2DGeneric",
                "TSP2D",
                TSP2DGenericConfigPage::new
        );

        AlgorithmRegistry.register(tspAlgorithmDescriptor);

        AlgorithmDescriptor bitStringOneMaxAlgorithmDescriptor = new AlgorithmDescriptor(
                BitStringGenericAlgorithm.class,
                "BitStringGeneric",
                "BitStringOneMax",
                BitStringGenericConfigPage::new
        );
        AlgorithmRegistry.register(bitStringOneMaxAlgorithmDescriptor);

        AlgorithmDescriptor bitStringLeadingOnesAlgorithmDescriptor = new AlgorithmDescriptor(
                BitStringGenericAlgorithm.class,
                "BitStringLeadingOnesGeneric",
                "BitStringLeadingOnes",
                BitStringGenericConfigPage::new
        );
        AlgorithmRegistry.register(bitStringLeadingOnesAlgorithmDescriptor);


        AlgorithmDescriptor tsp2DACODescriptor = new AlgorithmDescriptor(
                TSP2DACO.class,
                "TSP2DACO",
                "TSP2D",
                TSP2DACOConfigPage::new
        );
        AlgorithmRegistry.register(tsp2DACODescriptor);

        AlgorithmDescriptor bitStringOneMaxACODescriptor = new AlgorithmDescriptor(
                BitStringACO.class,
                "BitStringACO",
                "BitStringOneMax",
                BitStringACOConfigPage::new
        );
        AlgorithmRegistry.register(bitStringOneMaxACODescriptor);

        AlgorithmDescriptor bitStringLeadingOnesACODescriptor = new AlgorithmDescriptor(
                BitStringACO.class,
                "BitStringLeadingOnesACO",
                "BitStringLeadingOnes",
                BitStringACOConfigPage::new
        );
        AlgorithmRegistry.register(bitStringLeadingOnesACODescriptor);


        ACOTypeRegistry.register("Standard", new StandardACOType());

        ACOTypeRegistry.register("MMAS", new MMASACOType());

        BitStringACOTypeRegistry.register("Standard", new StandardBitStringACOType());

        BitStringACOTypeRegistry.register("MMAS", new MMASBitStringACOType());

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

        registerOperator("TSP2D (1+1) EA",
                OperatorType.MUTATION_TSP,
                "/com/ea_framework/operatorConfig/TSP2DOneOneEA.fxml",
                TSP2D_One_One_EA::new
        );

        registerTermination(
                "Max Iterations",
                "/com/ea_framework/operatorConfig/MaxIterationTermination.fxml",
                MaxIterationsCondition::new
        );

        registerTermination(
                "Fitness Threshold",
                "/com/ea_framework/operatorConfig/FitnessThresholdTermination.fxml",
                FitnessThresholdCondition::new
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

    private static void registerTermination(String name, String fxmlPath, Supplier<TerminationCondition> instanceSupplier) {
        TerminationDescriptor descriptor = new TerminationDescriptor(
                name,
                fxmlPath,
                () -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(RegistryInitializer.class.getResource(fxmlPath));
                        Parent root = loader.load();
                        OperatorConfigController controller = loader.getController();
                        return new GenericOperatorUI(root, controller);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                },
                instanceSupplier
        );

        TerminationRegistry.register(name, descriptor);
    }

}