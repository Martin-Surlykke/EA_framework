package com.ea_framework.Configs;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public interface AlgorithmConfigUI {

    void bindTo(Object config);

    Map<String, Object> getConfigs();

    AlgorithmConfig buildAlgorithmConfig(Problem problem);

    boolean isReadyToProceed();

    void setOnReady(Runnable callback);

}
