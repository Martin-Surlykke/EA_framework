package com.ea_framework;

import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Registries.ProblemRegistry;
import com.ea_framework.Registries.SearchSpaceRegistry;
import com.ea_framework.SearchSpaces.BitStringSearchSpace;
import com.ea_framework.SearchSpaces.Graph2DSearchSpace;

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
                        null
                )
        );
    }
}
