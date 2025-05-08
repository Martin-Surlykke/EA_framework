package com.ea_framework.Creators;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Algorithms.TSPAlgorithm;
import com.ea_framework.Configs.TSP2DAlgorithmConfig;

public class TSP2DAlgorithmCreator implements AlgorithmCreator<TSP2DAlgorithmConfig>  {

    @Override
    public Algorithm<?> create(TSP2DAlgorithmConfig config) {
        return new TSPAlgorithm(config.fitness(), config.mutation(), config.choice(), config.distanceMatrix());
    }
}
