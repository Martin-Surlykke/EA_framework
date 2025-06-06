package com.ea_framework.Configs;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public interface AlgorithmConfigUI {

    // This interface defines the methods required for an AlgorithmConfig UI component

    // Binds the UI to a configuration object
    void bindTo(Object config);

    // Returns the current configuration as a Map
    Map<String, Object> getConfigs();

    // Builds an AlgorithmConfig object based on the current problem
    AlgorithmConfig buildAlgorithmConfig(Problem problem);

    // Checks if the UI is ready to proceed based on the completeness of the input
    boolean isReadyToProceed();

    // Sets a callback to be executed when the UI is ready
    void setOnReady(Runnable callback);

}
