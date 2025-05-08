package com.ea_framework.Creators;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.AlgorithmConfig;

public interface AlgorithmCreator<C extends AlgorithmConfig> {
    Algorithm<?> create(C config);
}
