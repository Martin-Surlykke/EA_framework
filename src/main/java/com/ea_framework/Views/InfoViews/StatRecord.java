package com.ea_framework.Views.InfoViews;

public record StatRecord (
    int iteration,
    int evaluations,

    Number fitness,

    int bestIteration,
    int bestEvaluation,

    Number bestFitness

    )
{}

