package com.ea_framework.MutationFunctions;

public interface MutationOperator<Var> {
    Var mutate(Var solution);
}