package com.ea_framework.View.InfoViews;

public record ConfigRecord(
    String problem,
    String file,
    String algorithm,
    String choiceFunction,
    String fitnessFunction,
    String mutationOperator,
    String startRoute
) { }
