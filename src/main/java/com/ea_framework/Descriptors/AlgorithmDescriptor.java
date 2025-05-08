package com.ea_framework.Descriptors;

import com.ea_framework.Problems.Problem;

import java.util.function.Supplier;

public record AlgorithmDescriptor (
    String name,
    String searchSpace,
    Supplier<Problem> creator
) {}
