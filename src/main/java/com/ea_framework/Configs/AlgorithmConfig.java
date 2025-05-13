package com.ea_framework.Configs;

import com.ea_framework.Problems.Problem;

import java.util.Map;

public interface AlgorithmConfig {

    void populate(Map<String, Object> raw, Problem problem);
}
