package com.ea_framework.Mutation;

public interface MutationOperator<Var> {
    Var mutate(Var solution);
}