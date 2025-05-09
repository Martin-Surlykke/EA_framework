package com.ea_framework;

import com.ea_framework.Algorithms.TSPAlgorithm;
import com.ea_framework.Configs.TSP2DConfig;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Filehandlers.BitStringFileHandler;
import com.ea_framework.Filehandlers.TSPFileHandler;
import com.ea_framework.Loaders.ProblemLoader;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Registries.AlgorithmRegistry;
import com.ea_framework.Registries.ProblemRegistry;
import com.ea_framework.Registries.SearchSpaceRegistry;
import com.ea_framework.SearchSpaces.BitStringSearchSpace;
import com.ea_framework.SearchSpaces.Graph2DSearchSpace;
import com.ea_framework.Views.ConfigViews.TSP2DConfigView;

import java.io.File;
import java.io.IOException;

public class RegistryInitializer {

    public static void initialize() {

        SearchSpaceRegistry.register(
                "BitString",
                BitStringSearchSpace::new
        );

        SearchSpaceRegistry.register(
                "Graph2D",
                Graph2DSearchSpace::new
        );


        ProblemRegistry.register(
                new ProblemDescriptor(
                        "BitStringProblem",
                        "BitString",
                        new ProblemLoader() {
                            @Override
                            public boolean isValid(File file) {
                                return true;
                            }

                            @Override
                            public Problem load(File file) throws IOException {
                                return BitStringFileHandler.parse(file.getAbsolutePath());
                            }
                        }
                )
        );

        ProblemRegistry.register(
                new ProblemDescriptor(
                        "TSP2D",
                        "Graph2D",
                        new ProblemLoader() {
                            @Override
                            public boolean isValid(File file) {
                                return true;
                            }

                            @Override
                            public Problem load(File file) throws IOException {
                                return TSPFileHandler.parse(file.getAbsolutePath());
                            }
                        }
                )
        );

        AlgorithmRegistry.register(new AlgorithmDescriptor<>(
                "TSPAlgorithm",
                "TSP2D",
                TSPAlgorithm::new,
                TSP2DConfigView::new
        ));
    }
}
