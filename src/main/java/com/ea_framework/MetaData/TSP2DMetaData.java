package com.ea_framework.MetaData;

import java.util.List;

public class TSP2DMetaData implements ProblemMetadata{
    @Override
    public String getName() {
        return "TSP2D";
    }

    @Override
    public List<String> getCompatibleAlgorithms() {
        return List.of("TSP2DAlgorithm",
                       "ACO",
                       "1+1(EA)",
                       "ACOMMAS",
                       "RLS");
    }

    @Override
    public String getSearchSpace() {
        return "GRAPH2D";
    }
}
