package com.ea_framework.Operators.MutationFunctions;

public interface MutationOperator<Var> {
    Var mutate(Var solution);
}