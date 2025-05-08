package com.ea_framework.Descriptors;

import com.ea_framework.Problems.Problem;

import java.util.function.Supplier;

public record ProblemDescriptor (
        String name,
        String SearchSpace,

        Supplier<Problem> creator
) {}
