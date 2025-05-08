package com.ea_framework.Creators;

import com.ea_framework.Algorithms.Algorithms;
import com.ea_framework.Configs.AlgorithmConfig;

public interface AlgorithmCreator<C extends AlgorithmConfig> {
    Algorithms<?> create(C config);
}
