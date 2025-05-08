package com.ea_framework.MetaData;

import java.util.List;

public class Graph2D implements SearchSpaceMetaData {
    @Override
    public String getName() {
        return "GRAPH2D";
    }

    @Override
    public List<String> getCompatibleProblems() {
        return List.of(
                "TSP"
        );
    }
}
