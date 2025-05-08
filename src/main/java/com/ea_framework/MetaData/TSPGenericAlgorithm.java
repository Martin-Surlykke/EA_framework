package com.ea_framework.MetaData;

import java.util.List;

public class TSPGenericAlgorithm implements AlgorithmsMetaData {
    @Override
    public String getName() {
        return "TSPGenericAlgorithm";
    }

    @Override
    public List<String> getCompatibleProblem() {
        return List.of(
                "TSP"
        );
    }

    @Override
    public String getSearchSpace() {
        return "GRAPH2D";
    }
}
