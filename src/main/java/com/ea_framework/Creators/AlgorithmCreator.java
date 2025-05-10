package com.ea_framework.Creators;

import com.ea_framework.Algorithms.Algorithm;

public interface AlgorithmCreator<C> {
    Algorithm<?> create(C config);
}
