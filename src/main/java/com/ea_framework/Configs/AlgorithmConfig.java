package com.ea_framework.Configs;

import com.ea_framework.Problems.Problem;

import java.util.Map;

public interface AlgorithmConfig {

    // Interface for AlgorithmConfig which provides a method to populate the configuration

    void populate(Map<String, Object> raw, Problem problem);
    // Method to populate the configuration with raw data and a problem instance
}
