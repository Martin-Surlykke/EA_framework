package com.ea_framework.Creators;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Algorithms.TSPAlgorithm;
import com.ea_framework.Configs.TSP2DConfig;

public class TSP2DAlgorithmCreator implements AlgorithmCreator<TSP2DConfig>  {

    @Override
    public Algorithm<?> create(TSP2DConfig config) {
        return new TSPAlgorithm();
    }
}
